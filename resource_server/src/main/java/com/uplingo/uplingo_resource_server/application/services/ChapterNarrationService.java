package com.uplingo.uplingo_resource_server.application.services;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.uplingo.uplingo_resource_server.application.prompts.ChapterNarrationPrompt;
import com.uplingo.uplingo_resource_server.application.utils.AuthUtils;
import com.uplingo.uplingo_resource_server.application.utils.GenreToneHelper;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.AppUserRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.ChapterNarrationRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.ChapterRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.CourseRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.GrammarModuleRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.StoryCharacterRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.StoryRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.UserHobbyRepository;
import com.uplingo.uplingo_resource_server.model.entities.ChapterNarration;
import com.uplingo.uplingo_resource_server.model.entities.StoryCharacter;
import com.uplingo.uplingo_resource_server.model.entities.UserHobby;
import com.uplingo.uplingo_resource_server.model.enums.CharacterTypeEnum;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter_narration.ChapterNarrationCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter_narration.ChapterNarrationResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;

@Service
@RequiredArgsConstructor
public class ChapterNarrationService {
  private final AppUserRepository appUserRepository;
  private final StoryRepository storyRepository;
  private final UserHobbyRepository userHobbyRepository;
  private final GrammarModuleRepository grammarModuleRepository;
  private final CourseRepository courseRepository;
  private final StoryCharacterRepository storyCharacterRepository;
  private final ChapterRepository chapterRepository;
  private final ChapterNarrationRepository chapterNarrationRepository;
  private final CognitiveService cognitiveService;

  @Transactional(propagation = Propagation.REQUIRED)
  public ResponseDTO<ChapterNarrationResponseDTO> create(ChapterNarrationCreateDTO dto) {
    UUID requesterId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    if (!isAdmin && !requesterId.equals(requesterId)) {
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed");
    }

    var optUser = appUserRepository.findById(requesterId);
    if (optUser.isEmpty()) {
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "User not found");
    }
    var user = optUser.get();

    String userHobbies = userHobbyRepository.findAll().stream()
        .filter(h -> h.getUserId().equals(requesterId))
        .map(UserHobby::getDescription)
        .reduce("", (a, b) -> a.isEmpty() ? b : a + "; " + b);

    var optChapter = chapterRepository.findById(dto.chapterId());
    if (optChapter.isEmpty()) {
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Chapter not found");
    }
    var chapter = optChapter.get();

    long existingNarrationsCount = chapterNarrationRepository.countByChapterId(chapter.getId());
    if (existingNarrationsCount > 0) {
      return ResponseDTO.error(HttpStatus.CONFLICT, "This chapter already has a narration");
    }

    var optStory = storyRepository.findById(chapter.getStoryId());
    if (optStory.isEmpty()) {
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Story not found");
    }
    var story = optStory.get();

    var optGrammarModule = grammarModuleRepository.findById(story.getGrammarModuleId());
    if (optGrammarModule.isEmpty()) {
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Grammar module not found");
    }
    var grammarModule = optGrammarModule.get();

    var optCourse = courseRepository.findById(grammarModule.getCourseId());
    if (optCourse.isEmpty()) {
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Course not found");
    }
    var course = optCourse.get();

    var genre = story.getGenre();
    String genreTone = GenreToneHelper.getTone(genre);

    List<StoryCharacter> characters = storyCharacterRepository.findByStoryId(story.getId());

    Optional<StoryCharacter> mainCharacterOpt = characters.stream()
        .filter(c -> c.getCharacterType() == CharacterTypeEnum.MAIN)
        .findFirst();

    Optional<StoryCharacter> supportingCharacterOpt = characters.stream()
        .filter(c -> c.getCharacterType() == CharacterTypeEnum.SUPPORTING)
        .findFirst();

    if (mainCharacterOpt.isEmpty() || supportingCharacterOpt.isEmpty()) {
      return ResponseDTO.error(HttpStatus.CONFLICT, "Main or supporting character not found for the story");
    }

    StoryCharacter mainCharacter = mainCharacterOpt.get();
    StoryCharacter supportingCharacter = supportingCharacterOpt.get();

    String narrationPrompt = ChapterNarrationPrompt.generateFirstChapterNarrationPrompt(
        user.getName(),
        user.getDescription(),
        user.getReasonsLearn(),
        userHobbies,
        course.getName().toString(),
        course.getDescription(),
        grammarModule.getName(),
        grammarModule.getLevel(),
        genre,
        genreTone,
        story.getSetting(),
        mainCharacter.getName(),
        mainCharacter.getDescription(),
        supportingCharacter.getName(),
        supportingCharacter.getDescription());

    String narrationText = cognitiveService.generateText(narrationPrompt, 400).trim();

    ChapterNarration narration = ChapterNarration.builder()
        .chapterId(chapter.getId())
        .step(dto.step())
        .content(narrationText)
        .createdBy(requesterId)
        .build();

    narration = chapterNarrationRepository.save(narration);

    ChapterNarrationResponseDTO responseDTO = new ChapterNarrationResponseDTO(
        narration.getId(),
        narration.getChapterId(),
        narration.getStep(),
        narration.getContent(),
        narration.getCreatedAt());

    return ResponseDTO.created(responseDTO, "Chapter narration created successfully");
  }
}

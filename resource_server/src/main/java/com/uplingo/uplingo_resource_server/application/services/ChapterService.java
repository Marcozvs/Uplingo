package com.uplingo.uplingo_resource_server.application.services;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.uplingo.uplingo_resource_server.application.prompts.ChapterPrompt;
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
import com.uplingo.uplingo_resource_server.model.entities.Chapter;
import com.uplingo.uplingo_resource_server.model.entities.StoryCharacter;
import com.uplingo.uplingo_resource_server.model.entities.UserHobby;
import com.uplingo.uplingo_resource_server.model.enums.ChapterStatusEnum;
import com.uplingo.uplingo_resource_server.model.enums.CharacterTypeEnum;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter.ChapterCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter.ChapterResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter_answer.ChapterAnswerCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter_answer.ChapterAnswerResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter_narration.ChapterNarrationCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter_narration.ChapterNarrationResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;

@Service
@RequiredArgsConstructor
public class ChapterService {
  private final AppUserRepository appUserRepository;
  private final StoryRepository storyRepository;
  private final UserHobbyRepository userHobbyRepository;
  private final GrammarModuleRepository grammarModuleRepository;
  private final CourseRepository courseRepository;
  private final StoryCharacterRepository storyCharacterRepository;
  private final ChapterRepository chapterRepository;
  private final ChapterNarrationRepository chapterNarrationRepository;
  private final CognitiveService cognitiveService;
  private final ChapterNarrationService chapterNarrationService;
  private final ChapterAnswerService chapterAnswerService;

  @Transactional(propagation = Propagation.REQUIRED)
  public ResponseDTO<ChapterResponseDTO> create(ChapterCreateDTO dto) {
    UUID requesterId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    if (!isAdmin && !requesterId.equals(requesterId)) {
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed");
    }

    Optional<Chapter> existingChapter = chapterRepository.findByStoryIdAndChapterIndex(dto.storyId(), 1);
    if (existingChapter.isPresent()) {
      return ResponseDTO.error(HttpStatus.CONFLICT, "This story already has a first chapter");
    }

    Optional<Chapter> firstChapterAlreadyExists = chapterRepository.findByStoryIdAndChapterIndex(dto.storyId(), 1);
    if (firstChapterAlreadyExists.isPresent()) {
      return ResponseDTO.error(HttpStatus.CONFLICT, "A story cannot have more than one chapter at the moment");
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

    var optStory = storyRepository.findById(dto.storyId());
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

    String firstChapterTitlePrompt = ChapterPrompt.generateFirstChapterTitlePrompt(
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

    String firstChapterTitle = cognitiveService.generateText(firstChapterTitlePrompt, 50).trim();

    String introductionPrompt = ChapterPrompt.generateFirstChapterIntroductionPrompt(
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

    String introductionText = cognitiveService.generateText(introductionPrompt, 400).trim();

    Chapter newChapter = Chapter.builder()
        .userId(user.getId())
        .storyId(story.getId())
        .chapterIndex(1)
        .currentDialogIndex(1)
        .title(firstChapterTitle)
        .introduction(introductionText)
        .correctAnswers(0)
        .chapterStatus(ChapterStatusEnum.IN_PROGRESS)
        .createdBy(requesterId)
        .build();

    Chapter savedChapter = chapterRepository.save(newChapter);

    int nextStep = chapterNarrationRepository
        .findTop1ByChapterIdOrderByStepDesc(savedChapter.getId())
        .stream()
        .findFirst()
        .map(n -> n.getStep() + 1)
        .orElse(1);

    var narrationResponse = chapterNarrationService.create(
        new ChapterNarrationCreateDTO(savedChapter.getId(), nextStep));

    if (!narrationResponse.isSuccess()) {
      return ResponseDTO.error(HttpStatus.INTERNAL_SERVER_ERROR, narrationResponse.message());
    }

    var answerResponse = chapterAnswerService.create(
        new ChapterAnswerCreateDTO(savedChapter.getId(), nextStep));

    if (!answerResponse.isSuccess()) {
      return ResponseDTO.error(HttpStatus.INTERNAL_SERVER_ERROR, answerResponse.message());
    }

    List<ChapterNarrationResponseItemDTO> narrationItems = List.of();
    if (narrationResponse.isSuccess() && narrationResponse.data() != null) {
      var narrationResponseDto = narrationResponse.data();
      narrationItems = List.of(new ChapterNarrationResponseItemDTO(
          narrationResponseDto.id(),
          narrationResponseDto.chapterId(),
          narrationResponseDto.step(),
          narrationResponseDto.content(),
          narrationResponseDto.createdAt()));
    }

    List<ChapterAnswerResponseItemDTO> answerItems = List.of();
    if (answerResponse.isSuccess() && answerResponse.data() != null) {
      answerItems = answerResponse.data().stream()
          .map(answerResponseDto -> new ChapterAnswerResponseItemDTO(
              answerResponseDto.id(),
              answerResponseDto.chapterId(),
              answerResponseDto.characterId(),
              answerResponseDto.characterType(),
              answerResponseDto.characterName(),
              answerResponseDto.answerIndex(),
              answerResponseDto.step(),
              answerResponseDto.content(),
              answerResponseDto.correct()))
          .toList();
    }

    ChapterResponseDTO responseDTO = new ChapterResponseDTO(
        savedChapter.getId(),
        savedChapter.getUserId(),
        savedChapter.getStoryId(),
        savedChapter.getChapterIndex(),
        savedChapter.getCurrentDialogIndex(),
        savedChapter.getTitle(),
        savedChapter.getIntroduction(),
        savedChapter.getChapterStatus(),
        savedChapter.getCreatedBy(),
        null,
        narrationItems,
        answerItems);

    return ResponseDTO.created(responseDTO, "Chapter created successfully");
  }
}

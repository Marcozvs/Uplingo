package com.uplingo.uplingo_resource_server.application.services;

import com.uplingo.uplingo_resource_server.application.prompts.ChapterAnswerPrompt;
import com.uplingo.uplingo_resource_server.application.utils.AuthUtils;
import com.uplingo.uplingo_resource_server.application.utils.GenreToneHelper;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.AppUserRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.ChapterAnswerRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.ChapterRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.CourseRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.GrammarModuleRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.StoryCharacterRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.StoryRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.UserHobbyRepository;
import com.uplingo.uplingo_resource_server.model.entities.ChapterAnswer;
import com.uplingo.uplingo_resource_server.model.entities.StoryCharacter;
import com.uplingo.uplingo_resource_server.model.entities.UserHobby;
import com.uplingo.uplingo_resource_server.model.enums.CharacterTypeEnum;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter_answer.*;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;
import com.uplingo.uplingo_resource_server.ui.mappers.ChapterAnswerMapper;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChapterAnswerService {
  private final AppUserRepository appUserRepository;
  private final StoryRepository storyRepository;
  private final UserHobbyRepository userHobbyRepository;
  private final GrammarModuleRepository grammarModuleRepository;
  private final CourseRepository courseRepository;
  private final StoryCharacterRepository storyCharacterRepository;
  private final ChapterRepository chapterRepository;
  private final ChapterAnswerRepository chapterAnswerRepository;
  private final CognitiveService cognitiveService;
  private final ChapterAnswerMapper chapterAnswerMapper;

  @Transactional(propagation = Propagation.REQUIRED)
  public ResponseDTO<List<ChapterAnswerResponseDTO>> create(ChapterAnswerCreateDTO dto) {
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

    String firstSupportingCharacterSpeechPrompt = ChapterAnswerPrompt.generateFirstSupportingCharacterSpeechPrompt(
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

    String firstSupportingCharacterSpeech = cognitiveService.generateText(firstSupportingCharacterSpeechPrompt, 150)
        .trim();

    String firstMainCharacterSpeechPrompt = ChapterAnswerPrompt.generateFirstMainCharacterSpeechPrompt(
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
        supportingCharacter.getDescription(),
        firstSupportingCharacterSpeech);

    String firstMainCharacterSpeech = cognitiveService.generateText(firstMainCharacterSpeechPrompt, 150)
        .trim();

    String errorSpeechPrompt1_1 = ChapterAnswerPrompt.generateErrorSpeechPrompt(firstMainCharacterSpeech);
    String errorSpeech1_1 = cognitiveService.generateText(errorSpeechPrompt1_1, 150).trim();

    String errorSpeechPrompt1_2 = ChapterAnswerPrompt.generateErrorSpeechPrompt(firstMainCharacterSpeech);
    String errorSpeech1_2 = cognitiveService.generateText(errorSpeechPrompt1_2, 150).trim();

    String secondSupportingCharacterSpeechPrompt = ChapterAnswerPrompt.generateSecondSupportingCharacterSpeechPrompt(
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
        supportingCharacter.getDescription(),
        firstSupportingCharacterSpeech,
        firstMainCharacterSpeech);

    String secondSupportingCharacterSpeech = cognitiveService.generateText(secondSupportingCharacterSpeechPrompt, 150)
        .trim();

    String secondMainCharacterSpeechPrompt = ChapterAnswerPrompt.generateSecondMainCharacterSpeechPrompt(
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
        supportingCharacter.getDescription(),
        firstSupportingCharacterSpeech,
        firstMainCharacterSpeech,
        secondSupportingCharacterSpeech);

    String secondMainCharacterSpeech = cognitiveService.generateText(secondMainCharacterSpeechPrompt, 150)
        .trim();

    String errorSpeechPrompt2_1 = ChapterAnswerPrompt.generateErrorSpeechPrompt(secondMainCharacterSpeech);
    String errorSpeech2_1 = cognitiveService.generateText(errorSpeechPrompt2_1, 150).trim();

    String errorSpeechPrompt2_2 = ChapterAnswerPrompt.generateErrorSpeechPrompt(secondMainCharacterSpeech);
    String errorSpeech2_2 = cognitiveService.generateText(errorSpeechPrompt2_2, 150).trim();

    String thirdSupportingCharacterSpeechPrompt = ChapterAnswerPrompt.generateThirdSupportingCharacterSpeechPrompt(
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
        supportingCharacter.getDescription(),
        firstSupportingCharacterSpeech,
        firstMainCharacterSpeech,
        secondSupportingCharacterSpeech,
        secondMainCharacterSpeech);

    String thirdSupportingCharacterSpeech = cognitiveService.generateText(thirdSupportingCharacterSpeechPrompt, 150)
        .trim();

    String thirdMainCharacterSpeechPrompt = ChapterAnswerPrompt.generateThirdMainCharacterSpeechPrompt(
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
        supportingCharacter.getDescription(),
        firstSupportingCharacterSpeech,
        firstMainCharacterSpeech,
        secondSupportingCharacterSpeech,
        secondMainCharacterSpeech,
        thirdSupportingCharacterSpeech);

    String thirdMainCharacterSpeech = cognitiveService.generateText(thirdMainCharacterSpeechPrompt, 150).trim();

    String errorSpeechPrompt3_1 = ChapterAnswerPrompt.generateErrorSpeechPrompt(thirdMainCharacterSpeech);
    String errorSpeech3_1 = cognitiveService.generateText(errorSpeechPrompt3_1, 150).trim();

    String errorSpeechPrompt3_2 = ChapterAnswerPrompt.generateErrorSpeechPrompt(thirdMainCharacterSpeech);
    String errorSpeech3_2 = cognitiveService.generateText(errorSpeechPrompt3_2, 150).trim();

    List<ChapterAnswer> createdAnswers = new ArrayList<>();
    Collections.addAll(
        createdAnswers,
        saveChapterAnswer(chapter.getId(), supportingCharacter.getId(), CharacterTypeEnum.SUPPORTING,
            supportingCharacter.getName(), 1,
            firstSupportingCharacterSpeech, true, dto.nextStep(), requesterId),
        saveChapterAnswer(chapter.getId(), mainCharacter.getId(), CharacterTypeEnum.MAIN, mainCharacter.getName(), 1,
            firstMainCharacterSpeech,
            true, dto.nextStep(), requesterId),
        saveChapterAnswer(chapter.getId(), mainCharacter.getId(), CharacterTypeEnum.MAIN, mainCharacter.getName(), 1,
            errorSpeech1_1, false,
            dto.nextStep(), requesterId),
        saveChapterAnswer(chapter.getId(), mainCharacter.getId(), CharacterTypeEnum.MAIN, mainCharacter.getName(), 1,
            errorSpeech1_2, false,
            dto.nextStep(), requesterId),
        saveChapterAnswer(chapter.getId(), supportingCharacter.getId(), CharacterTypeEnum.SUPPORTING,
            supportingCharacter.getName(), 2,
            secondSupportingCharacterSpeech, true, dto.nextStep(), requesterId),
        saveChapterAnswer(chapter.getId(), mainCharacter.getId(), CharacterTypeEnum.MAIN, mainCharacter.getName(), 2,
            secondMainCharacterSpeech,
            true, dto.nextStep(), requesterId),
        saveChapterAnswer(chapter.getId(), mainCharacter.getId(), CharacterTypeEnum.MAIN, mainCharacter.getName(), 2,
            errorSpeech2_1, false,
            dto.nextStep(), requesterId),
        saveChapterAnswer(chapter.getId(), mainCharacter.getId(), CharacterTypeEnum.MAIN, mainCharacter.getName(), 2,
            errorSpeech2_2, false,
            dto.nextStep(), requesterId),
        saveChapterAnswer(chapter.getId(), supportingCharacter.getId(), CharacterTypeEnum.SUPPORTING,
            supportingCharacter.getName(), 3,
            thirdSupportingCharacterSpeech, true, dto.nextStep(), requesterId),
        saveChapterAnswer(chapter.getId(), mainCharacter.getId(), CharacterTypeEnum.MAIN, mainCharacter.getName(), 3,
            thirdMainCharacterSpeech,
            true, dto.nextStep(), requesterId),
        saveChapterAnswer(chapter.getId(), mainCharacter.getId(), CharacterTypeEnum.MAIN, mainCharacter.getName(), 3,
            errorSpeech3_1, false,
            dto.nextStep(), requesterId),
        saveChapterAnswer(chapter.getId(), mainCharacter.getId(), CharacterTypeEnum.MAIN, mainCharacter.getName(), 3,
            errorSpeech3_2, false,
            dto.nextStep(), requesterId));

    List<ChapterAnswerResponseDTO> mapped = createdAnswers.stream()
        .map(chapterAnswerMapper::toResponseDto)
        .toList();

    Map<Integer, List<ChapterAnswerResponseDTO>> answersByIndex = mapped.stream()
        .collect(Collectors.groupingBy(ChapterAnswerResponseDTO::answerIndex));

    List<ChapterAnswerResponseDTO> orderedResponseList = new ArrayList<>();

    answersByIndex.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .forEach(entry -> {
          List<ChapterAnswerResponseDTO> group = entry.getValue();

          ChapterAnswerResponseDTO supporting = group.stream()
              .filter(a -> a.characterType() == CharacterTypeEnum.SUPPORTING)
              .findFirst()
              .orElseThrow();

          List<ChapterAnswerResponseDTO> mainAnswers = group.stream()
              .filter(a -> a.characterType() == CharacterTypeEnum.MAIN)
              .collect(Collectors.toCollection(ArrayList::new));

          Collections.shuffle(mainAnswers);
          orderedResponseList.add(supporting);
          orderedResponseList.addAll(mainAnswers);
        });

    return ResponseDTO.created(orderedResponseList, "Chapter answers created successfully");
  }

  private ChapterAnswer saveChapterAnswer(
      UUID chapterId,
      UUID characterId,
      CharacterTypeEnum characterType,
      String characterName,
      Integer answerIndex,
      String content,
      boolean correct,
      int step,
      UUID createdBy) {
    ChapterAnswer answer = ChapterAnswer.builder()
        .chapterId(chapterId)
        .characterId(characterId)
        .characterType(characterType)
        .characterName(characterName)
        .answerIndex(answerIndex)
        .content(content)
        .correct(correct)
        .step(step)
        .createdBy(createdBy)
        .build();

    return chapterAnswerRepository.save(answer);
  }
}

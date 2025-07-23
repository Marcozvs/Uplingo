package com.uplingo.uplingo_resource_server.application.services;

import com.uplingo.uplingo_resource_server.application.prompts.StoryCharacterPrompt;
import com.uplingo.uplingo_resource_server.application.prompts.StoryPrompt;
import com.uplingo.uplingo_resource_server.application.utils.AuthUtils;
import com.uplingo.uplingo_resource_server.application.utils.GenreToneHelper;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.AppUserRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.ChapterAnswerRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.ChapterMemoryRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.ChapterNarrationRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.ChapterRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.CourseRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.GrammarModuleRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.StoryCharacterRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.StoryRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.UserChapterResultRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.UserEnergyRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.UserHobbyRepository;
import com.uplingo.uplingo_resource_server.model.entities.Chapter;
import com.uplingo.uplingo_resource_server.model.entities.ChapterAnswer;
import com.uplingo.uplingo_resource_server.model.entities.Story;
import com.uplingo.uplingo_resource_server.model.entities.UserChapterResult;
import com.uplingo.uplingo_resource_server.model.entities.UserEnergy;
import com.uplingo.uplingo_resource_server.model.entities.UserHobby;
import com.uplingo.uplingo_resource_server.model.enums.ChapterStatusEnum;
import com.uplingo.uplingo_resource_server.model.enums.CharacterTypeEnum;
import com.uplingo.uplingo_resource_server.model.enums.GenreEnum;
import com.uplingo.uplingo_resource_server.model.enums.GradeEnum;
import com.uplingo.uplingo_resource_server.model.enums.NarratorTypeEnum;
import com.uplingo.uplingo_resource_server.model.enums.StoryStatusEnum;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter.ChapterCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter.ChapterResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter.ChapterResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter_answer.ChapterAnswerResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.story.StoryCheckAnswerReponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.story.StoryCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.story.StoryResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.story.StoryResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.story_charactere.StoryCharacterCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.story_charactere.StoryCharacterResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_chapter_result.UserChapterResultResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_chapter_result.UserChapterResultResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.mappers.ChapterMapper;
import com.uplingo.uplingo_resource_server.ui.mappers.ChapterNarrationMapper;
import com.uplingo.uplingo_resource_server.ui.mappers.StoryMapper;
import com.uplingo.uplingo_resource_server.ui.mappers.UserChapterResultMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class StoryService {

  private final StoryRepository storyRepository;
  private final AppUserRepository appUserRepository;
  private final CourseRepository courseRepository;
  private final GrammarModuleRepository grammarModuleRepository;
  private final UserHobbyRepository userHobbyRepository;
  private final ChapterRepository chapterRepository;
  private final ChapterAnswerRepository chapterAnswerRepository;
  private final UserChapterResultRepository userChapterResultRepository;
  private final ChapterNarrationRepository chapterNarrationRepository;
  private final ChapterMemoryRepository chapterMemoryRepository;
  private final StoryCharacterRepository storyCharacterRepository;
  private final StoryCharacterService storyCharacterService;
  private final CognitiveService cognitiveService;
  private final ChapterService chapterService;
  private final UserEnergyRepository userEnergyRepository;
  private final StoryMapper storyMapper;
  private final UserChapterResultMapper userChapterResultMapper;
  private final ChapterMapper chapterMapper;
  private final ChapterNarrationMapper chapterNarrationMapper;

  @Transactional
  public ResponseDTO<StoryResponseDTO> create(StoryCreateDTO dto) {
    UUID requesterId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    if (!isAdmin && !requesterId.equals(requesterId)) {
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed");
    }

    var optUser = appUserRepository.findById(requesterId);
    if (optUser.isEmpty()) {
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "User not found");
    }

    int availableEnergy = userEnergyRepository.countByUserIdAndConsumedFalseAndValidUntilAfter(
        requesterId, LocalDateTime.now());
    if (availableEnergy <= 0) {
      return ResponseDTO.error(HttpStatus.FORBIDDEN,
          "No energy available. Please request energy from the administrator.");
    }

    boolean hasUnfinishedStory = storyRepository
        .existsByUserIdAndStoryStatusNot(requesterId, StoryStatusEnum.COMPLETED);
    if (hasUnfinishedStory) {
      return ResponseDTO.error(HttpStatus.CONFLICT, "You already have an unfinished story");
    }

    var optModule = grammarModuleRepository.findById(dto.grammarModuleId());
    if (optModule.isEmpty()) {
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Grammar module not found");
    }

    var grammarModule = optModule.get();
    var optCourse = courseRepository.findById(grammarModule.getCourseId());
    if (optCourse.isEmpty()) {
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Course not found");
    }

    var user = optUser.get();
    var course = optCourse.get();

    String userHobbies = userHobbyRepository.findAll().stream()
        .filter(h -> h.getUserId().equals(requesterId))
        .map(UserHobby::getDescription)
        .reduce("", (a, b) -> a.isEmpty() ? b : a + "; " + b);

    GenreEnum[] genres = GenreEnum.values();
    GenreEnum selectedGenre = genres[ThreadLocalRandom.current().nextInt(genres.length)];
    String genreTone = GenreToneHelper.getTone(selectedGenre);

    NarratorTypeEnum[] narrators = NarratorTypeEnum.values();
    NarratorTypeEnum selectedNarrator = narrators[ThreadLocalRandom.current().nextInt(narrators.length)];

    String themePrompt = StoryPrompt.generateStoryThemePrompt(
        user.getName(),
        user.getDescription(),
        user.getReasonsLearn(),
        userHobbies,
        course.getName().toString(),
        course.getDescription(),
        grammarModule.getName(),
        grammarModule.getLevel(),
        selectedGenre,
        genreTone);

    String generatedTheme = cognitiveService.generateText(themePrompt, 100).trim();

    String settingPrompt = StoryPrompt.generateStorySettingPrompt(
        user.getName(),
        user.getDescription(),
        user.getReasonsLearn(),
        userHobbies,
        course.getName().toString(),
        course.getDescription(),
        grammarModule.getName(),
        grammarModule.getLevel(),
        selectedGenre,
        genreTone,
        generatedTheme);

    String generatedSetting = cognitiveService.generateText(settingPrompt, 200).trim();

    String mainNamePrompt = StoryCharacterPrompt.generateStoryMainCharacterNamePrompt(
        user.getName(),
        user.getDescription(),
        user.getReasonsLearn(),
        userHobbies,
        course.getName().toString(),
        course.getDescription(),
        grammarModule.getName(),
        grammarModule.getLevel(),
        selectedGenre,
        genreTone,
        generatedTheme,
        generatedSetting);

    String mainCharacterName = cognitiveService.generateText(mainNamePrompt, 50).trim();

    String mainCharacterDescriptionPrompt = StoryCharacterPrompt.generateStoryMainCharacterDescriptionPrompt(
        user.getName(),
        user.getDescription(),
        user.getReasonsLearn(),
        userHobbies,
        course.getName().toString(),
        course.getDescription(),
        grammarModule.getName(),
        grammarModule.getLevel(),
        selectedGenre,
        genreTone,
        generatedTheme,
        generatedSetting,
        mainCharacterName);

    String mainCharacterDescription = cognitiveService.generateText(mainCharacterDescriptionPrompt, 400).trim();

    String supportingNamePrompt = StoryCharacterPrompt.generateStorySupportingCharacterNamePrompt(
        user.getName(),
        user.getDescription(),
        user.getReasonsLearn(),
        userHobbies,
        course.getName().toString(),
        course.getDescription(),
        grammarModule.getName(),
        grammarModule.getLevel(),
        selectedGenre,
        genreTone,
        generatedTheme,
        generatedSetting,
        mainCharacterName,
        mainCharacterDescription);

    String supportingCharacterName = cognitiveService.generateText(supportingNamePrompt, 50).trim();

    String supportingCharacterDescriptionPrompt = StoryCharacterPrompt
        .generateStorySupportingCharacterDescriptionPrompt(
            user.getName(),
            user.getDescription(),
            user.getReasonsLearn(),
            userHobbies,
            course.getName().toString(),
            course.getDescription(),
            grammarModule.getName(),
            grammarModule.getLevel(),
            selectedGenre,
            genreTone,
            generatedTheme,
            generatedSetting,
            mainCharacterName,
            mainCharacterDescription,
            supportingCharacterName);

    String supportingCharacterDescription = cognitiveService.generateText(supportingCharacterDescriptionPrompt, 400)
        .trim();

    String storyTitlePrompt = StoryPrompt.generateStoryTitlePrompt(
        user.getName(),
        user.getDescription(),
        user.getReasonsLearn(),
        userHobbies,
        course.getName().toString(),
        course.getDescription(),
        grammarModule.getName(),
        grammarModule.getLevel(),
        selectedGenre,
        genreTone,
        generatedTheme,
        generatedSetting,
        mainCharacterName,
        mainCharacterDescription,
        supportingCharacterName,
        supportingCharacterDescription);

    String generatedStoryTitle = cognitiveService.generateText(storyTitlePrompt, 50).trim();

    String storyDescriptionPrompt = StoryPrompt.generateStoryDescriptionPrompt(
        user.getName(),
        user.getDescription(),
        user.getReasonsLearn(),
        userHobbies,
        course.getName().toString(),
        course.getDescription(),
        grammarModule.getName(),
        grammarModule.getLevel(),
        selectedGenre,
        genreTone,
        generatedTheme,
        generatedSetting,
        mainCharacterName,
        mainCharacterDescription,
        supportingCharacterName,
        supportingCharacterDescription,
        generatedStoryTitle);

    String generatedStoryDescription = cognitiveService.generateText(storyDescriptionPrompt, 400).trim();

    Story story = Story.builder()
        .userId(requesterId)
        .grammarModuleId(grammarModule.getId())
        .title(generatedStoryTitle)
        .backOver(generatedStoryDescription)
        .storyStatus(StoryStatusEnum.IN_PROGRESS)
        .genre(selectedGenre)
        .narratorType(selectedNarrator)
        .setting(generatedSetting)
        .createdBy(requesterId)
        .build();

    storyRepository.save(story);

    storyCharacterService.create(new StoryCharacterCreateDTO(
        story.getId(),
        mainCharacterName,
        mainCharacterDescription,
        CharacterTypeEnum.MAIN));

    storyCharacterService.create(new StoryCharacterCreateDTO(
        story.getId(),
        supportingCharacterName,
        supportingCharacterDescription,
        CharacterTypeEnum.SUPPORTING));

    ChapterCreateDTO chapterDto = new ChapterCreateDTO(requesterId, story.getId());
    ResponseDTO<ChapterResponseDTO> chapterResponse = chapterService.create(chapterDto);

    if (!chapterResponse.isSuccess() || chapterResponse.data() == null) {
      return ResponseDTO.error(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create chapter");
    }

    UserChapterResultResponseDTO resultDTO = chapterResponse.data().result();

    List<UserChapterResultResponseItemDTO> resultsList = resultDTO != null
        ? List.of(new UserChapterResultResponseItemDTO(
            resultDTO.id(),
            resultDTO.userId(),
            resultDTO.chapterId(),
            resultDTO.grade(),
            resultDTO.xpEarned(),
            resultDTO.completedAt()))
        : List.of();

    ChapterResponseItemDTO chapterItem = new ChapterResponseItemDTO(
        chapterResponse.data().id(),
        chapterResponse.data().userId(),
        chapterResponse.data().storyId(),
        chapterResponse.data().chapterIndex(),
        chapterResponse.data().currentDialogIndex(),
        chapterResponse.data().title(),
        chapterResponse.data().introduction(),
        chapterResponse.data().narrations() != null ? chapterResponse.data().narrations() : List.of(),
        chapterResponse.data().answers() != null ? chapterResponse.data().answers() : List.of(),
        resultsList);

    List<ChapterResponseItemDTO> chaptersList = List.of(chapterItem);

    List<StoryCharacterResponseItemDTO> charactersList = storyCharacterRepository.findByStoryId(story.getId()).stream()
        .map(c -> new StoryCharacterResponseItemDTO(
            c.getId(),
            c.getStoryId(),
            c.getName(),
            c.getCharacterType(),
            c.getDescription()))
        .toList();

    StoryResponseDTO responseDTO = new StoryResponseDTO(
        story.getId(),
        story.getUserId(),
        story.getGrammarModuleId(),
        story.getTitle(),
        story.getBackOver(),
        story.getStoryStatus(),
        story.getGenre(),
        story.getCreatedAt(),
        charactersList,
        chaptersList);

    var energies = userEnergyRepository.findAll().stream()
        .filter(e -> e.getUserId().equals(requesterId))
        .filter(e -> !e.isConsumed())
        .filter(e -> e.getValidUntil().isAfter(LocalDateTime.now()))
        .sorted((e1, e2) -> e1.getValidUntil().compareTo(e2.getValidUntil()))
        .toList();

    if (!energies.isEmpty()) {
      UserEnergy energyToConsume = energies.get(0);
      energyToConsume.setConsumed(true);
      energyToConsume.setUpdatedAt(LocalDateTime.now());
      energyToConsume.setUpdatedBy(requesterId);

      userEnergyRepository.save(energyToConsume);
    }

    return ResponseDTO.created(responseDTO, "Story created successfully");
  }

  @Transactional(readOnly = true)
  public ResponseDTO<StoryResponseDTO> readById(UUID id) {
    Optional<Story> opt = storyRepository.findById(id);
    if (opt.isEmpty())
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Story not found");

    Story story = opt.get();
    List<Chapter> chapters = chapterRepository.findByStoryId(story.getId());

    List<ChapterResponseItemDTO> chapterDtos = chapters.stream().map(chapter -> {
      var narrations = chapterNarrationRepository.findByChapterId(chapter.getId()).stream()
          .map(chapterNarrationMapper::toResponseItemDto)
          .toList();

      var answers = chapterAnswerRepository.findByChapterId(chapter.getId()).stream()
          .map(answer -> new ChapterAnswerResponseItemDTO(
              answer.getId(),
              answer.getChapterId(),
              answer.getCharacterId(),
              answer.getCharacterType(),
              answer.getCharacterName(),
              answer.getAnswerIndex(),
              answer.getStep(),
              answer.getContent(),
              answer.getAnswerIndex() < chapter.getCurrentDialogIndex() ? answer.isCorrect() : null))
          .toList();

      var results = userChapterResultRepository.findByChapterId(chapter.getId()).stream()
          .map(userChapterResultMapper::toResponseItemDto)
          .toList();

      return chapterMapper.toResponseItemDto(chapter, narrations, answers, results);
    }).toList();

    List<StoryCharacterResponseItemDTO> charactersList = storyCharacterRepository.findByStoryId(story.getId()).stream()
        .map(c -> new StoryCharacterResponseItemDTO(
            c.getId(),
            c.getStoryId(),
            c.getName(),
            c.getCharacterType(),
            c.getDescription()))
        .toList();

    StoryResponseDTO dto = storyMapper.toResponseDto(story, chapterDtos, charactersList);
    return ResponseDTO.success(dto);
  }

  @Transactional(readOnly = true)
  public ResponseDTO<List<StoryResponseItemDTO>> readAll() {
    UUID requesterId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    List<Story> stories;

    if (isAdmin) {
      stories = storyRepository.findAll();
    } else {
      stories = storyRepository.findAll().stream()
          .filter(story -> story.getUserId().equals(requesterId))
          .toList();
    }

    return ResponseDTO.success(storyMapper.toResponseItemDtoList(stories));
  }

  @Transactional
  public ResponseDTO<Void> delete(UUID id) {
    Optional<Story> opt = storyRepository.findById(id);
    if (opt.isEmpty()) {
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Story not found");
    }

    Story story = opt.get();
    UUID requesterId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    if (!isAdmin && !requesterId.equals(story.getUserId())) {
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed");
    }

    List<Chapter> chapters = chapterRepository.findByStoryId(id);

    for (Chapter chapter : chapters) {
      UUID chapterId = chapter.getId();

      chapterNarrationRepository.deleteByChapterId(chapterId);
      chapterAnswerRepository.deleteByChapterId(chapterId);
      userChapterResultRepository.deleteByChapterId(chapterId);
      chapterMemoryRepository.deleteByConversationId(chapterId.toString());
    }

    chapterRepository.deleteAll(chapters);
    storyCharacterRepository.deleteByStoryId(id);
    storyRepository.deleteById(id);

    return ResponseDTO.success(null, "Story and related data deleted");
  }

  @Transactional
  public ResponseDTO<StoryCheckAnswerReponseDTO> checkAnswer(UUID answerId) {
    UUID userId = AuthUtils.getUserId();

    Optional<ChapterAnswer> optAnswer = chapterAnswerRepository.findById(answerId);
    if (optAnswer.isEmpty()) {
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Answer not found");
    }
    ChapterAnswer answer = optAnswer.get();

    Optional<Chapter> optChapter = chapterRepository.findById(answer.getChapterId());
    if (optChapter.isEmpty()) {
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Chapter not found");
    }
    Chapter chapter = optChapter.get();

    boolean alreadyEvaluated = userChapterResultRepository
        .existsByUserIdAndChapterId(userId, chapter.getId());
    if (alreadyEvaluated) {
      return ResponseDTO.error(HttpStatus.CONFLICT, "Chapter already evaluated");
    }

    int answerIndex = answer.getAnswerIndex();
    int currentIndex = chapter.getCurrentDialogIndex();

    if (answerIndex < currentIndex) {
      return ResponseDTO.error(HttpStatus.CONFLICT, "This line has already been evaluated");
    }

    if (answerIndex > currentIndex) {
      return ResponseDTO.error(HttpStatus.CONFLICT, "There is a previous line that hasn't been evaluated yet");
    }

    boolean isCorrect = answer.isCorrect();

    if (isCorrect) {
      chapter.setCorrectAnswers(chapter.getCorrectAnswers() + 1);
    }

    if (answerIndex == 3) {
      int correct = chapter.getCorrectAnswers();
      GradeEnum grade;
      int xp;

      switch (correct) {
        case 3 -> {
          grade = GradeEnum.A;
          xp = 100;
        }
        case 2 -> {
          grade = GradeEnum.B;
          xp = 75;
        }
        case 1 -> {
          grade = GradeEnum.C;
          xp = 50;
        }
        default -> {
          grade = GradeEnum.F;
          xp = 0;
        }
      }

      UserChapterResult result = UserChapterResult.builder()
          .userId(userId)
          .chapterId(chapter.getId())
          .grade(grade)
          .xpEarned(xp)
          .completedAt(LocalDateTime.now())
          .createdBy(userId)
          .updatedBy(userId)
          .build();

      userChapterResultRepository.save(result);

      chapter.setChapterStatus(ChapterStatusEnum.COMPLETED);
      chapter.setUpdatedAt(LocalDateTime.now());
      chapter.setUpdatedBy(userId);
      chapterRepository.save(chapter);

      Optional<Story> optStory = storyRepository.findById(chapter.getStoryId());
      if (optStory.isPresent()) {
        Story story = optStory.get();
        story.setStoryStatus(StoryStatusEnum.COMPLETED);
        storyRepository.save(story);
      }

      chapter.setCurrentDialogIndex(4);
      chapter.setUpdatedAt(LocalDateTime.now());
      chapter.setUpdatedBy(userId);
      chapterRepository.save(chapter);

      return ResponseDTO.success(new StoryCheckAnswerReponseDTO(isCorrect, result, currentIndex));
    }

    chapter.setCurrentDialogIndex(currentIndex + 1);
    chapter.setUpdatedAt(LocalDateTime.now());
    chapter.setUpdatedBy(userId);
    chapterRepository.save(chapter);

    return ResponseDTO.success(new StoryCheckAnswerReponseDTO(isCorrect, null, currentIndex));
  }
}

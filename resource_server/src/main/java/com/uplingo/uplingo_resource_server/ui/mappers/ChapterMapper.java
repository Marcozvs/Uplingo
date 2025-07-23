package com.uplingo.uplingo_resource_server.ui.mappers;

import com.uplingo.uplingo_resource_server.model.entities.Chapter;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter.ChapterCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter.ChapterResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter.ChapterResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter_answer.ChapterAnswerResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter_narration.ChapterNarrationResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_chapter_result.UserChapterResultResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_chapter_result.UserChapterResultResponseItemDTO;

import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChapterMapper {

  Chapter toEntity(ChapterCreateDTO dto);

  default ChapterResponseDTO toResponseDto(
      Chapter entity,
      List<ChapterNarrationResponseItemDTO> narrations,
      List<ChapterAnswerResponseItemDTO> answers,
      UserChapterResultResponseDTO result) {
    return new ChapterResponseDTO(
        entity.getId(),
        entity.getUserId(),
        entity.getStoryId(),
        entity.getChapterIndex(),
        entity.getCurrentDialogIndex(),
        entity.getTitle(),
        entity.getIntroduction(),
        entity.getChapterStatus(),
        entity.getUpdatedBy(),
        result,
        narrations != null ? narrations : new ArrayList<>(),
        answers != null ? answers : new ArrayList<>());
  }

  default ChapterResponseItemDTO toResponseItemDto(
      Chapter entity,
      List<ChapterNarrationResponseItemDTO> narrations,
      List<ChapterAnswerResponseItemDTO> answers,
      List<UserChapterResultResponseItemDTO> results) {
    return new ChapterResponseItemDTO(
        entity.getId(),
        entity.getUserId(),
        entity.getStoryId(),
        entity.getChapterIndex(),
        entity.getCurrentDialogIndex(),
        entity.getTitle(),
        entity.getIntroduction(),
        narrations != null ? narrations : new ArrayList<>(),
        answers != null ? answers : new ArrayList<>(),
        results != null ? results : new ArrayList<>());
  }

  ChapterResponseItemDTO toResponseItemDto(Chapter entity);

  List<ChapterResponseItemDTO> toResponseItemDtoList(List<Chapter> entities);
}

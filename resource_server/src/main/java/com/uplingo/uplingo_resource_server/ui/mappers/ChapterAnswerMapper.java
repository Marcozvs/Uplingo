package com.uplingo.uplingo_resource_server.ui.mappers;

import com.uplingo.uplingo_resource_server.model.entities.ChapterAnswer;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter_answer.*;

import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChapterAnswerMapper {

  ChapterAnswer toEntity(ChapterAnswerCreateDTO dto);

  ChapterAnswerResponseDTO toResponseDto(ChapterAnswer entity);

  ChapterAnswerResponseItemDTO toResponseItemDto(ChapterAnswer entity);

  List<ChapterAnswerResponseItemDTO> toResponseItemDtoList(List<ChapterAnswer> entities);

  default ChapterAnswerResponseItemDTO toResponseItemDtoWithCorrectConditional(ChapterAnswer entity, int currentStep) {
    return new ChapterAnswerResponseItemDTO(
        entity.getId(),
        entity.getChapterId(),
        entity.getCharacterId(),
        entity.getCharacterType(),
        entity.getCharacterName(),
        entity.getAnswerIndex(),
        entity.getStep(),
        entity.getContent(),
        entity.isCorrect());
  }
}

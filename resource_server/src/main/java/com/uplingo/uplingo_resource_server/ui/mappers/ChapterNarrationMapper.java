package com.uplingo.uplingo_resource_server.ui.mappers;

import com.uplingo.uplingo_resource_server.model.entities.ChapterNarration;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter_narration.*;

import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChapterNarrationMapper {

  ChapterNarration toEntity(ChapterNarrationCreateDTO dto);

  ChapterNarrationResponseDTO toResponseDto(ChapterNarration entity);

  ChapterNarrationResponseItemDTO toResponseItemDto(ChapterNarration entity);

  List<ChapterNarrationResponseItemDTO> toResponseItemDtoList(List<ChapterNarration> entities);
}

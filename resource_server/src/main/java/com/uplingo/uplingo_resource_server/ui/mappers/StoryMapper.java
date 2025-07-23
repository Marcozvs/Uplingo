package com.uplingo.uplingo_resource_server.ui.mappers;

import com.uplingo.uplingo_resource_server.model.entities.Story;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter.ChapterResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.story.StoryCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.story.StoryResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.story.StoryResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.story_charactere.StoryCharacterResponseItemDTO;

import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoryMapper {

  Story toEntity(StoryCreateDTO dto);

  default StoryResponseDTO toResponseDto(Story entity, List<ChapterResponseItemDTO> chapters, List<StoryCharacterResponseItemDTO> characters) {
    return new StoryResponseDTO(
        entity.getId(),
        entity.getUserId(),
        entity.getGrammarModuleId(),
        entity.getTitle(),
        entity.getBackOver(),
        entity.getStoryStatus(),
        entity.getGenre(),
        entity.getCreatedAt(),
        characters,
        chapters);
  }

  StoryResponseItemDTO toResponseItemDto(Story entity);

  List<StoryResponseItemDTO> toResponseItemDtoList(List<Story> entities);
}

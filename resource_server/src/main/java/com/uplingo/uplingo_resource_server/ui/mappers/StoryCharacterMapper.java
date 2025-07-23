package com.uplingo.uplingo_resource_server.ui.mappers;

import com.uplingo.uplingo_resource_server.model.entities.StoryCharacter;
import com.uplingo.uplingo_resource_server.ui.dtos.story_charactere.StoryCharacterResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.story_charactere.StoryCharacterResponseItemDTO;

import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoryCharacterMapper {

  StoryCharacterResponseDTO toResponseDto(StoryCharacter entity);

  StoryCharacterResponseItemDTO toResponseItemDto(StoryCharacter entity);

  List<StoryCharacterResponseDTO> toResponseDtoList(List<StoryCharacter> entities);

  List<StoryCharacterResponseItemDTO> toResponseItemDtoList(List<StoryCharacter> entities);
}

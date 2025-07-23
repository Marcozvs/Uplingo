package com.uplingo.uplingo_resource_server.ui.mappers;

import com.uplingo.uplingo_resource_server.model.entities.UserHobby;
import com.uplingo.uplingo_resource_server.ui.dtos.user_hobby.UserHobbyCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_hobby.UserHobbyResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_hobby.UserHobbyResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_hobby.UserHobbyUpdateDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserHobbyMapper {

  UserHobby toEntity(UserHobbyCreateDTO dto);

  @Mapping(target = "id", ignore = true)
  void updateEntityFromDto(UserHobbyUpdateDTO dto, @MappingTarget UserHobby entity);

  UserHobbyResponseDTO toResponseDto(UserHobby entity);

  UserHobbyResponseItemDTO toResponseItemDto(UserHobby entity);

  List<UserHobbyResponseItemDTO> toResponseItemDtoList(List<UserHobby> entities);
}

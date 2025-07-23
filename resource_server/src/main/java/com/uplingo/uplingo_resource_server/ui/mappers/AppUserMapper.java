package com.uplingo.uplingo_resource_server.ui.mappers;

import com.uplingo.uplingo_resource_server.model.entities.AppUser;
import com.uplingo.uplingo_resource_server.ui.dtos.app_user.AppUserResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.app_user.AppUserResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.app_user.AppUserUpdateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_hobby.UserHobbyResponseItemDTO;

import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppUserMapper {

  AppUser toEntity(AppUserUpdateDTO dto);

  @Mapping(target = "id", ignore = true)
  void updateEntityFromDto(AppUserUpdateDTO dto, @MappingTarget AppUser entity);

  default AppUserResponseDTO toResponseDto(
      AppUser user,
      List<UserHobbyResponseItemDTO> hobbies,
      Integer energyCount,
      Integer totalXp) {
    return new AppUserResponseDTO(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getRoles(),
        user.getDescription(),
        user.getReasonsLearn(),
        user.getUserLanguage(),
        user.getPhotoUrl(),
        user.isBanned(),
        user.getBannedAt(),
        energyCount,
        totalXp,
        user.isFirstTime(),
        hobbies);

  }

  AppUserResponseItemDTO toResponseItemDto(AppUser entity);

  List<AppUserResponseItemDTO> toResponseItemDtoList(List<AppUser> entities);
}

package com.uplingo.uplingo_authorization_server.ui.mappers.user;

import org.mapstruct.*;

import com.uplingo.uplingo_authorization_server.model.entities.AppUser;
import com.uplingo.uplingo_authorization_server.ui.dtos.user.AppUserCreateDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

  AppUser toEntity(AppUserCreateDTO dto);
}

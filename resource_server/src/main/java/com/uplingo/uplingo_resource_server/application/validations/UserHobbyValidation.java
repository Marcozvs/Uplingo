package com.uplingo.uplingo_resource_server.application.validations;

import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_hobby.UserHobbyCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_hobby.UserHobbyUpdateDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserHobbyValidation {

  public static ResponseDTO<Void> validateCreateDTO(UserHobbyCreateDTO dto) {
    if (dto == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "User hobby must not be null");
    }

    if (dto.userId() == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "User ID must not be null");
    }

    if (!StringUtils.hasText(dto.description())) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Hobby description must not be empty");
    }

    return null;
  }

  public static ResponseDTO<Void> validateUpdateDTO(UserHobbyUpdateDTO dto) {
    if (dto == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "User hobby must not be null");
    }

    if (dto.id() == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Hobby ID must not be null");
    }

    if (dto.updatedBy() == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "UpdatedBy must not be null");
    }

    if (!StringUtils.hasText(dto.description())) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Hobby description must not be empty");
    }

    return null;
  }
}

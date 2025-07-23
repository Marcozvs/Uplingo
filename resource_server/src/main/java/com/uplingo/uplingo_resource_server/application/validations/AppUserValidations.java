package com.uplingo.uplingo_resource_server.application.validations;

import com.uplingo.uplingo_resource_server.ui.dtos.app_user.AppUserUpdateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AppUserValidations {

  public static ResponseDTO<Void> validateUpdateDTO(AppUserUpdateDTO dto) {
    if (dto == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Update data must not be null");
    }

    if (dto.description() != null && dto.description().length() > 1000) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Description must be at most 1000 characters");
    }

    if (dto.reasonsLearn() != null && dto.reasonsLearn().length() > 1000) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Reasons to learn must be at most 1000 characters");
    }

    if (dto.userLanguage() == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Language must not be null");
    }

    return null;
  }
}

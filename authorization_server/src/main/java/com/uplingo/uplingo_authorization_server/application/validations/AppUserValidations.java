package com.uplingo.uplingo_authorization_server.application.validations;

import com.uplingo.uplingo_authorization_server.infrastructure.repositories.AppUserRepository;
import com.uplingo.uplingo_authorization_server.ui.dtos.response.ResponseDTO;
import com.uplingo.uplingo_authorization_server.ui.dtos.user.AppUserCreateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public final class AppUserValidations {

  private AppUserValidations() {
  }

  public static ResponseDTO<Void> validateCreateDTO(AppUserCreateDTO dto, AppUserRepository repository) {
    if (dto == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "User must not be null");
    }

    if (!StringUtils.hasText(dto.name())) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "User name must not be empty");
    }

    if (!StringUtils.hasText(dto.email())) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "User email must not be empty");
    }

    if (!isValidEmail(dto.email())) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Invalid email format: " + dto.email());
    }

    if (repository.existsByEmail(dto.email())) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Email already registered: " + dto.email());
    }

    if (!StringUtils.hasText(dto.password())) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "User password must not be empty");
    }

    if (dto.roles() == null || dto.roles().isEmpty()) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "User must have at least one role");
    }

    if (dto.roles().stream().anyMatch(r -> !StringUtils.hasText(r))) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "User roles must not contain empty values");
    }

    if (dto.banned() == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "User banned status must not be null");
    }

    return null;
  }

  private static boolean isValidEmail(String email) {
    return email != null && email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$");
  }
}

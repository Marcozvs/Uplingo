package com.uplingo.uplingo_resource_server.application.validations;

import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_energy.UserEnergyCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_energy.UserEnergyUpdateDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserEnergyValidations {

  public static ResponseDTO<Void> validateCreateDTO(UserEnergyCreateDTO dto) {
    if (dto == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "User energy data must not be null");
    }

    if (dto.userId() == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "User ID must be provided");
    }

    if (dto.validUntil() == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Valid until must be provided");
    }

    if (dto.validUntil().isBefore(LocalDateTime.now())) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Valid until must be a future date");
    }

    return null;
  }

  public static ResponseDTO<Void> validateUpdateDTO(UserEnergyUpdateDTO dto) {
    if (dto == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "User energy data must not be null");
    }

    if (dto.userId() == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "User ID must be provided");
    }

    if (dto.updatedBy() == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "UpdatedBy must be provided");
    }

    if (dto.validUntil() == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Valid until must be provided");
    }

    if (dto.validUntil().isBefore(LocalDateTime.now())) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Valid until must be a future date");
    }

    return null;
  }
}

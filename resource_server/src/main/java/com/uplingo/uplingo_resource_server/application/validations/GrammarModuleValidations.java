package com.uplingo.uplingo_resource_server.application.validations;

import com.uplingo.uplingo_resource_server.ui.dtos.grammar_module.GrammarModuleCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.grammar_module.GrammarModuleUpdateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GrammarModuleValidations {

  public static ResponseDTO<Void> validateCreateDTO(GrammarModuleCreateDTO dto) {
    if (dto == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Grammar module must not be null");
    }

    if (!StringUtils.hasText(dto.name())) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Grammar module name must not be empty");
    }

    if (!StringUtils.hasText(dto.description())) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Grammar module description must not be empty");
    }

    if (dto.courseId() == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Course ID must be specified");
    }

    if (dto.level() == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Level must be specified");
    }

    return null;
  }

  public static ResponseDTO<Void> validateUpdateDTO(GrammarModuleUpdateDTO dto) {
    if (dto == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Grammar module must not be null");
    }

    if (!StringUtils.hasText(dto.name())) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Grammar module name must not be empty");
    }

    if (!StringUtils.hasText(dto.description())) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Grammar module description must not be empty");
    }

    if (dto.courseId() == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Course ID must be specified");
    }

    if (dto.level() == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Level must be specified");
    }

    if (dto.updatedBy() == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "UpdatedBy must be specified");
    }

    return null;
  }
}

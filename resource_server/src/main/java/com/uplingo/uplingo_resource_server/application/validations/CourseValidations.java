package com.uplingo.uplingo_resource_server.application.validations;

import com.uplingo.uplingo_resource_server.ui.dtos.course.CourseCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.course.CourseUpdateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CourseValidations {

  public static ResponseDTO<Void> validateCreateDTO(CourseCreateDTO dto) {
    if (dto == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Course must not be null");
    }

    if (dto.name() == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Course name must be specified");
    }

    if (!StringUtils.hasText(dto.description())) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Course description must not be empty");
    }

    return null;
  }

  public static ResponseDTO<Void> validateUpdateDTO(CourseUpdateDTO dto) {
    if (dto == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Course must not be null");
    }

    if (dto.name() == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Course name must be specified");
    }

    if (!StringUtils.hasText(dto.description())) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Course description must not be empty");
    }

    if (dto.updatedBy() == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "UpdatedBy must be specified");
    }

    return null;
  }
}

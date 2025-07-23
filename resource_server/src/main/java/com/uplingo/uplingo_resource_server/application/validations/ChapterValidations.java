package com.uplingo.uplingo_resource_server.application.validations;

import com.uplingo.uplingo_resource_server.ui.dtos.chapter.ChapterCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ChapterValidations {

  public static ResponseDTO<Void> validateCreateDTO(ChapterCreateDTO dto) {
    if (dto == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Chapter must not be null");
    }

    if (dto.userId() == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "User ID must be specified");
    }

    if (dto.storyId() == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Story ID must be specified");
    }

    return null;
  }
}

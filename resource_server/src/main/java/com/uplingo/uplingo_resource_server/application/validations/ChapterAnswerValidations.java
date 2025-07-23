package com.uplingo.uplingo_resource_server.application.validations;

import com.uplingo.uplingo_resource_server.ui.dtos.chapter_answer.ChapterAnswerCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ChapterAnswerValidations {

  public static ResponseDTO<Void> validateCreateDTO(ChapterAnswerCreateDTO dto) {
    if (dto == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Chapter answer must not be null");
    }

    if (dto.chapterId() == null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Chapter ID must be specified");
    }

    return null;
  }
}

package com.uplingo.uplingo_resource_server.application.validations;

import com.uplingo.uplingo_resource_server.application.exceptions.BadRequestException;
import com.uplingo.uplingo_resource_server.ui.dtos.user_chapter_result.UserChapterResultCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_chapter_result.UserChapterResultUpdateDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserChapterResultValidations {

  public static void validateCreateDTO(UserChapterResultCreateDTO dto) {
    if (dto == null) {
      throw new BadRequestException("UserChapterResult must not be null");
    }
    if (dto.userId() == null) {
      throw new BadRequestException("User ID must be specified");
    }
    if (dto.chapterId() == null) {
      throw new BadRequestException("Chapter ID must be specified");
    }
    if (dto.grade() == null) {
      throw new BadRequestException("Grade must be specified");
    }
    if (dto.xpEarned() == null || dto.xpEarned() < 0) {
      throw new BadRequestException("XP earned must be zero or positive");
    }
    if (dto.completedAt() == null || dto.completedAt().isAfter(LocalDateTime.now())) {
      throw new BadRequestException("CompletedAt must be in the past or now");
    }
  }

  public static void validateUpdateDTO(UserChapterResultUpdateDTO dto) {
    if (dto == null) {
      throw new BadRequestException("UserChapterResult must not be null");
    }
    if (dto.grade() != null && dto.grade().name().isBlank()) {
      throw new BadRequestException("Grade must be a valid value");
    }
    if (dto.xpEarned() != null && dto.xpEarned() < 0) {
      throw new BadRequestException("XP earned must be zero or positive");
    }
    if (dto.completedAt() != null && dto.completedAt().isAfter(LocalDateTime.now())) {
      throw new BadRequestException("CompletedAt must be in the past or now");
    }
    if (dto.updatedBy() == null) {
      throw new BadRequestException("UpdatedBy must be specified");
    }
  }
}

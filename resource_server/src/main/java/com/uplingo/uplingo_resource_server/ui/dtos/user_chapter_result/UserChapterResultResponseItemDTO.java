package com.uplingo.uplingo_resource_server.ui.dtos.user_chapter_result;

import java.time.LocalDateTime;
import java.util.UUID;

import com.uplingo.uplingo_resource_server.model.enums.GradeEnum;

import jakarta.validation.constraints.NotNull;

public record UserChapterResultResponseItemDTO(
    @NotNull UUID id,
    @NotNull UUID userId,
    @NotNull UUID chapterId,
    @NotNull GradeEnum grade,
    @NotNull Integer xpEarned,
    @NotNull LocalDateTime completedAt) {
}

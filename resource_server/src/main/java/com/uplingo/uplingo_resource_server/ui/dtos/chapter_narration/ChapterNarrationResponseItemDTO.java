package com.uplingo.uplingo_resource_server.ui.dtos.chapter_narration;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record ChapterNarrationResponseItemDTO(
    @NotNull UUID id,
    @NotNull UUID chapterId,
    @NotNull Integer step,
    @NotNull String content,
    @NotNull LocalDateTime createdAt) {
}

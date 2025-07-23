package com.uplingo.uplingo_resource_server.ui.dtos.chapter_narration;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record ChapterNarrationCreateDTO(
    @NotNull UUID chapterId,
    @NotNull Integer step) {
}

package com.uplingo.uplingo_resource_server.ui.dtos.chapter;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record ChapterCreateDTO(
    @NotNull UUID userId,
    @NotNull UUID storyId) {
}
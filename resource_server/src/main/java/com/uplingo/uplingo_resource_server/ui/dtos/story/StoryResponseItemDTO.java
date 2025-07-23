package com.uplingo.uplingo_resource_server.ui.dtos.story;

import java.util.UUID;

import com.uplingo.uplingo_resource_server.model.enums.StoryStatusEnum;

import jakarta.validation.constraints.NotNull;

public record StoryResponseItemDTO(
    @NotNull UUID id,
    @NotNull UUID userId,
    @NotNull UUID grammarModuleId,
    @NotNull String title,
    @NotNull StoryStatusEnum storyStatus
    ) {
}
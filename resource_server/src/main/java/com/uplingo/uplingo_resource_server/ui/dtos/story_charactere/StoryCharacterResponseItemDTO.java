package com.uplingo.uplingo_resource_server.ui.dtos.story_charactere;

import java.util.UUID;

import com.uplingo.uplingo_resource_server.model.enums.CharacterTypeEnum;

import jakarta.validation.constraints.NotNull;

public record StoryCharacterResponseItemDTO(
    @NotNull UUID id,
    @NotNull UUID storyId,
    @NotNull String name,
    @NotNull CharacterTypeEnum characterType,
    @NotNull String description
) {}

package com.uplingo.uplingo_resource_server.ui.dtos.story_charactere;

import java.util.UUID;

import com.uplingo.uplingo_resource_server.model.enums.CharacterTypeEnum;

import jakarta.validation.constraints.NotNull;

public record StoryCharacterCreateDTO(
    @NotNull UUID storyId,
    @NotNull String name,
    @NotNull String description,
    @NotNull CharacterTypeEnum characterType
) {}

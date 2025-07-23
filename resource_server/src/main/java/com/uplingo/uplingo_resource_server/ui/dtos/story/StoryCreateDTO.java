package com.uplingo.uplingo_resource_server.ui.dtos.story;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record StoryCreateDTO(
    @NotNull UUID grammarModuleId
) {}

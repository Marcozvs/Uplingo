package com.uplingo.uplingo_resource_server.ui.dtos.grammar_module;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record GrammarModuleResponseItemDTO(
    @NotNull UUID id,
    @NotNull UUID courseId,
    @NotNull String name,
    @NotNull String description 
) {}
package com.uplingo.uplingo_resource_server.ui.dtos.grammar_module;

import java.util.UUID;

import com.uplingo.uplingo_resource_server.model.enums.LevelEnum;

import jakarta.validation.constraints.NotNull;

public record GrammarModuleUpdateDTO(
    @NotNull UUID id,
    @NotNull UUID courseId,
    @NotNull String name,
    @NotNull String description,
    @NotNull LevelEnum level,
    @NotNull UUID updatedBy
) {}

package com.uplingo.uplingo_resource_server.ui.dtos.grammar_module;

import java.time.LocalDateTime;
import java.util.UUID;

import com.uplingo.uplingo_resource_server.model.enums.LevelEnum;

import jakarta.validation.constraints.NotNull;

public record GrammarModuleResponseDTO(
    @NotNull UUID id,
    @NotNull UUID courseId,
    @NotNull String name,
    @NotNull String description,
    @NotNull LevelEnum level,
    @NotNull LocalDateTime createdAt) {
}

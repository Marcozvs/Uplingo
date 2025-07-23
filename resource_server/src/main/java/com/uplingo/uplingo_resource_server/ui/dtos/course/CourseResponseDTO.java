package com.uplingo.uplingo_resource_server.ui.dtos.course;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.uplingo.uplingo_resource_server.model.enums.CourseNameEnum;
import com.uplingo.uplingo_resource_server.ui.dtos.grammar_module.GrammarModuleResponseItemDTO;

import jakarta.validation.constraints.NotNull;

public record CourseResponseDTO(
    @NotNull UUID id,
    @NotNull CourseNameEnum name,
    @NotNull String description,
    @NotNull LocalDateTime createdAt,
    @NotNull List<GrammarModuleResponseItemDTO> modules
) {}

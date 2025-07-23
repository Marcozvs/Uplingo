package com.uplingo.uplingo_resource_server.ui.dtos.course;

import java.util.UUID;

import com.uplingo.uplingo_resource_server.model.enums.CourseNameEnum;

import jakarta.validation.constraints.NotNull;

public record CourseResponseItemDTO(
    @NotNull UUID id,
    @NotNull CourseNameEnum name,
    @NotNull String description
) {}
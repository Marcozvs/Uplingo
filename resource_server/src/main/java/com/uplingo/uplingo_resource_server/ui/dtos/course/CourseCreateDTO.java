package com.uplingo.uplingo_resource_server.ui.dtos.course;

import com.uplingo.uplingo_resource_server.model.enums.CourseNameEnum;

import jakarta.validation.constraints.NotNull;

public record CourseCreateDTO(
    @NotNull CourseNameEnum name,
    @NotNull String description
) {}
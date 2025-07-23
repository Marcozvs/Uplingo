package com.uplingo.uplingo_resource_server.ui.dtos.user_hobby;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record UserHobbyResponseDTO(
    @NotNull UUID id,
    @NotNull String description) {
}

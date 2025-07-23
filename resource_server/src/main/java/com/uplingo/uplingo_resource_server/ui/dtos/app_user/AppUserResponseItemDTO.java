package com.uplingo.uplingo_resource_server.ui.dtos.app_user;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record AppUserResponseItemDTO(
    @NotNull UUID id,
    @NotNull String name,
    @NotNull String photoUrl,
    @NotNull String email,
    @NotNull Boolean banned) {
}

package com.uplingo.uplingo_resource_server.ui.dtos.user_energy;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record UserEnergyUpdateDTO(
    @NotNull UUID id,
    @NotNull UUID userId,
    @NotNull LocalDateTime validUntil,
    @NotNull Boolean consumed,
    @NotNull UUID updatedBy) {
}

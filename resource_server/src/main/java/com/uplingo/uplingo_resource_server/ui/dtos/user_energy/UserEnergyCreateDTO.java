package com.uplingo.uplingo_resource_server.ui.dtos.user_energy;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record UserEnergyCreateDTO(
    @NotNull UUID userId,
    @NotNull LocalDateTime validUntil
) {}

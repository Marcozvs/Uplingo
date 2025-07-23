package com.uplingo.uplingo_authorization_server.ui.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import com.uplingo.uplingo_authorization_server.model.enums.LanguageEnum;

public record AppUserCreateDTO(
    @NotBlank String name,
    @NotBlank String email,
    @NotBlank String password,
    @NotNull List<String> roles,
    @NotNull LanguageEnum userLanguage,
    @NotNull String photoUrl,
    @NotNull Boolean banned,
    @NotNull Boolean firstTime
) {}


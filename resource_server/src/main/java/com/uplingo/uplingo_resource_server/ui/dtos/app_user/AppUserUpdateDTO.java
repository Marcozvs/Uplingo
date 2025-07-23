package com.uplingo.uplingo_resource_server.ui.dtos.app_user;

import com.uplingo.uplingo_resource_server.model.enums.LanguageEnum;

import jakarta.validation.constraints.NotNull;

public record AppUserUpdateDTO(
    @NotNull String description,
    @NotNull String reasonsLearn,
    @NotNull LanguageEnum userLanguage,
    @NotNull Boolean firstTime) {
}

// DTO Response com lista de roles como List<String>
package com.uplingo.uplingo_resource_server.ui.dtos.app_user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.uplingo.uplingo_resource_server.model.enums.LanguageEnum;
import com.uplingo.uplingo_resource_server.ui.dtos.user_hobby.UserHobbyResponseItemDTO;

import jakarta.validation.constraints.NotNull;

public record AppUserResponseDTO(
    @NotNull UUID id,
    @NotNull String name,
    @NotNull String email,
    @NotNull List<String> roles,
    String description,
    String reasonsLearn,
    @NotNull LanguageEnum userLanguage,
    @NotNull String photoUrl,
    @NotNull Boolean banned,
    LocalDateTime bannedAt,
    @NotNull Integer energy,
    @NotNull Integer totalXP,
    @NotNull Boolean firstTime,
    @NotNull List<UserHobbyResponseItemDTO> hobbies) {
}

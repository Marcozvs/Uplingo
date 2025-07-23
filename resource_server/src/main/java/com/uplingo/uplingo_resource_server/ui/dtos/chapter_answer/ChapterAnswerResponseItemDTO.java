package com.uplingo.uplingo_resource_server.ui.dtos.chapter_answer;

import java.util.UUID;

import com.uplingo.uplingo_resource_server.model.enums.CharacterTypeEnum;

import jakarta.validation.constraints.NotNull;

public record ChapterAnswerResponseItemDTO(
    @NotNull UUID id,
    @NotNull UUID chapterId,
    @NotNull UUID characterId,
    @NotNull CharacterTypeEnum characterType,
    @NotNull String characterName,
    @NotNull Integer answerIndex,
    @NotNull Integer step,
    @NotNull String content,
    Boolean correct) {
}

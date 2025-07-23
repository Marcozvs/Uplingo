package com.uplingo.uplingo_resource_server.ui.dtos.chapter_answer;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record ChapterAnswerCreateDTO(
    @NotNull UUID chapterId,
    @NotNull Integer nextStep) {
}

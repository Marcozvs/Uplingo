package com.uplingo.uplingo_resource_server.ui.dtos.chapter;

import java.util.List;
import java.util.UUID;

import com.uplingo.uplingo_resource_server.ui.dtos.chapter_answer.ChapterAnswerResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter_narration.ChapterNarrationResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_chapter_result.UserChapterResultResponseItemDTO;

import jakarta.validation.constraints.NotNull;

public record ChapterResponseItemDTO(
    @NotNull UUID id,
    @NotNull UUID userId,
    @NotNull UUID storyId,
    @NotNull Integer chapterIndex,
    @NotNull Integer currentDialogIndex,
    @NotNull String title,
    @NotNull String introduction,
    @NotNull List<ChapterNarrationResponseItemDTO> narrations,
    @NotNull List<ChapterAnswerResponseItemDTO> answers,
    @NotNull List<UserChapterResultResponseItemDTO> results) {
}
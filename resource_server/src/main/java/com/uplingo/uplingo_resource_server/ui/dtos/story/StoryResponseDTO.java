package com.uplingo.uplingo_resource_server.ui.dtos.story;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.uplingo.uplingo_resource_server.model.enums.GenreEnum;
import com.uplingo.uplingo_resource_server.model.enums.StoryStatusEnum;
import com.uplingo.uplingo_resource_server.ui.dtos.chapter.ChapterResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.story_charactere.StoryCharacterResponseItemDTO;

import jakarta.validation.constraints.NotNull;

public record StoryResponseDTO(
    @NotNull UUID id,
    @NotNull UUID userId,
    @NotNull UUID grammarModuleId,
    @NotNull String title,
    @NotNull String backOver,
    @NotNull StoryStatusEnum storyStatus,
    @NotNull GenreEnum genre,
    @NotNull LocalDateTime createdAt,
    @NotNull List<StoryCharacterResponseItemDTO> characters,
    @NotNull List<ChapterResponseItemDTO> chapters) {}

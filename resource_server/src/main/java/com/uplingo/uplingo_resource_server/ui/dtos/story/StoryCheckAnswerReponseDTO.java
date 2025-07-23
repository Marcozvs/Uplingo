package com.uplingo.uplingo_resource_server.ui.dtos.story;

import com.uplingo.uplingo_resource_server.model.entities.UserChapterResult;

public record StoryCheckAnswerReponseDTO(
  Boolean correct,
  UserChapterResult result,
  Integer currentDialogIndex
) {}

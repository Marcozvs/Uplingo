package com.uplingo.uplingo_resource_server.application.services;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uplingo.uplingo_resource_server.application.utils.AuthUtils;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.AppUserRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.StoryCharacterRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.StoryRepository;
import com.uplingo.uplingo_resource_server.model.entities.StoryCharacter;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.story_charactere.StoryCharacterCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.story_charactere.StoryCharacterResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoryCharacterService {
  private final AppUserRepository appUserRepository;
  private final StoryRepository storyRepository;
  private final StoryCharacterRepository storyCharacterRepository;

  @Transactional
  public ResponseDTO<StoryCharacterResponseDTO> create(StoryCharacterCreateDTO dto) {
    UUID requesterId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    if (!isAdmin && !requesterId.equals(requesterId)) {
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed");
    }

    var optUser = appUserRepository.findById(requesterId);
    if (optUser.isEmpty()) {
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "User not found");
    }

    var optStory = storyRepository.findById(dto.storyId());
    if (optStory.isEmpty()) {
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Story not found");
    }

    StoryCharacter newCharacter = StoryCharacter.builder()
        .storyId(dto.storyId())
        .name(dto.name())
        .description(dto.description())
        .characterType(dto.characterType())
        .createdBy(requesterId)
        .build();

    StoryCharacter savedCharacter = storyCharacterRepository.save(newCharacter);

    StoryCharacterResponseDTO responseDTO = new StoryCharacterResponseDTO(
        savedCharacter.getId(),
        savedCharacter.getStoryId(),
        savedCharacter.getName(),
        savedCharacter.getDescription(),
        savedCharacter.getCharacterType(),
        savedCharacter.getCreatedAt());

    return ResponseDTO.created(responseDTO, "Character created successfully");
  }
}

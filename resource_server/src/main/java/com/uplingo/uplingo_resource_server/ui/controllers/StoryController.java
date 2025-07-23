package com.uplingo.uplingo_resource_server.ui.controllers;

import com.uplingo.uplingo_resource_server.application.services.StoryService;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.story.StoryCheckAnswerReponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.story.StoryCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.story.StoryResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.story.StoryResponseItemDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
public class StoryController {

  private final StoryService storyService;

  @GetMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ResponseDTO<StoryResponseDTO>> readById(@PathVariable UUID id) {
    ResponseDTO<StoryResponseDTO> response = storyService.readById(id);
    return ResponseEntity.status(response.status()).body(response);
  }

  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ResponseDTO<List<StoryResponseItemDTO>>> readAll() {
    ResponseDTO<List<StoryResponseItemDTO>> response = storyService.readAll();
    return ResponseEntity.status(response.status()).body(response);
  }

  @PostMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ResponseDTO<StoryResponseDTO>> create(
      @Valid @RequestBody StoryCreateDTO dto) {
    ResponseDTO<StoryResponseDTO> response = storyService.create(dto);
    return ResponseEntity.status(response.status()).body(response);
  }

  @PostMapping("/check-answer/{answerId}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ResponseDTO<StoryCheckAnswerReponseDTO>> checkAnswer(
      @PathVariable UUID answerId) {
    ResponseDTO<StoryCheckAnswerReponseDTO> response = storyService.checkAnswer(answerId);
    return ResponseEntity.status(response.status()).body(response);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable UUID id) {
    ResponseDTO<Void> response = storyService.delete(id);
    return ResponseEntity.status(response.status()).body(response);
  }
}

package com.uplingo.uplingo_resource_server.ui.controllers;

import com.uplingo.uplingo_resource_server.application.services.UserHobbyService;
import com.uplingo.uplingo_resource_server.ui.dtos.user_hobby.UserHobbyCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_hobby.UserHobbyUpdateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_hobby.UserHobbyResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_hobby.UserHobbyResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/hobbies")
@RequiredArgsConstructor
public class UserHobbyController {

  private final UserHobbyService userHobbyService;

  @GetMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ResponseDTO<UserHobbyResponseDTO>> readById(@PathVariable UUID id) {
    ResponseDTO<UserHobbyResponseDTO> response = userHobbyService.readById(id);
    return ResponseEntity.status(response.status()).body(response);
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ResponseDTO<List<UserHobbyResponseItemDTO>>> readAll() {
    ResponseDTO<List<UserHobbyResponseItemDTO>> response = userHobbyService.readAll();
    return ResponseEntity.status(response.status()).body(response);
  }

  @PostMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ResponseDTO<UserHobbyResponseDTO>> create(@Valid @RequestBody UserHobbyCreateDTO dto) {
    ResponseDTO<UserHobbyResponseDTO> response = userHobbyService.create(dto);
    return ResponseEntity.status(response.status()).body(response);
  }

  @PutMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ResponseDTO<UserHobbyResponseDTO>> update(
      @PathVariable UUID id,
      @Valid @RequestBody UserHobbyUpdateDTO dto) {
    ResponseDTO<UserHobbyResponseDTO> response = userHobbyService.update(id, dto);
    return ResponseEntity.status(response.status()).body(response);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable UUID id) {
    ResponseDTO<Void> response = userHobbyService.delete(id);
    return ResponseEntity.status(response.status()).body(response);
  }
}

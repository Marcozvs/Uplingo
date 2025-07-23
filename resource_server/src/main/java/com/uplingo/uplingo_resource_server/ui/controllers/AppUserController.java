package com.uplingo.uplingo_resource_server.ui.controllers;

import com.uplingo.uplingo_resource_server.application.services.AppUserService;
import com.uplingo.uplingo_resource_server.ui.dtos.app_user.AppUserResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.app_user.AppUserResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.app_user.AppUserUpdateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AppUserController {

  private final AppUserService appUserService;

  @GetMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ResponseDTO<AppUserResponseDTO>> readById(@PathVariable UUID id) {
    ResponseDTO<AppUserResponseDTO> response = appUserService.readById(id);
    return ResponseEntity.status(response.status()).body(response);
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ResponseDTO<List<AppUserResponseItemDTO>>> readAll() {
    ResponseDTO<List<AppUserResponseItemDTO>> response = appUserService.readAll();
    return ResponseEntity.status(response.status()).body(response);
  }

  @PutMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ResponseDTO<AppUserResponseDTO>> update(
      @PathVariable UUID id,
      @Valid @RequestBody AppUserUpdateDTO dto) {
    ResponseDTO<AppUserResponseDTO> response = appUserService.update(id, dto);
    return ResponseEntity.status(response.status()).body(response);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable UUID id) {
    ResponseDTO<Void> response = appUserService.delete(id);
    return ResponseEntity.status(response.status()).body(response);
  }

  @PutMapping("/{id}/ban")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ResponseDTO<Void>> ban(@PathVariable UUID id) {
    ResponseDTO<Void> response = appUserService.ban(id);
    return ResponseEntity.status(response.status()).body(response);
  }
}

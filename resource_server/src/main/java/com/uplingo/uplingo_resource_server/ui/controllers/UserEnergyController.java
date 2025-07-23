package com.uplingo.uplingo_resource_server.ui.controllers;

import com.uplingo.uplingo_resource_server.application.services.UserEnergyService;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_energy.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/energy")
@RequiredArgsConstructor
public class UserEnergyController {

  private final UserEnergyService userEnergyService;

  @GetMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ResponseDTO<UserEnergyResponseDTO>> readById(@PathVariable UUID id) {
    ResponseDTO<UserEnergyResponseDTO> response = userEnergyService.readById(id);
    return ResponseEntity.status(response.status()).body(response);
  }

  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ResponseDTO<List<UserEnergyResponseItemDTO>>> readAll(
      @RequestParam(required = false) UUID userId) {

    ResponseDTO<List<UserEnergyResponseItemDTO>> response = userEnergyService.readAll(userId);
    return ResponseEntity.status(response.status()).body(response);
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ResponseDTO<UserEnergyResponseDTO>> create(@Valid @RequestBody UserEnergyCreateDTO dto) {
    ResponseDTO<UserEnergyResponseDTO> response = userEnergyService.create(dto);
    return ResponseEntity.status(response.status()).body(response);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ResponseDTO<UserEnergyResponseDTO>> update(
      @PathVariable UUID id,
      @Valid @RequestBody UserEnergyUpdateDTO dto) {
    ResponseDTO<UserEnergyResponseDTO> response = userEnergyService.update(id, dto);
    return ResponseEntity.status(response.status()).body(response);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable UUID id) {
    ResponseDTO<Void> response = userEnergyService.delete(id);
    return ResponseEntity.status(response.status()).body(response);
  }

  @PostMapping("/request")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ResponseDTO<Void>> requestEnergy() {
    ResponseDTO<Void> response = userEnergyService.requestEnergy();
    return ResponseEntity.status(response.status()).body(response);
  }
}

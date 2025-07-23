package com.uplingo.uplingo_resource_server.ui.controllers;

import com.uplingo.uplingo_resource_server.application.services.GrammarModuleService;
import com.uplingo.uplingo_resource_server.ui.dtos.grammar_module.GrammarModuleCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.grammar_module.GrammarModuleUpdateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.grammar_module.GrammarModuleResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.grammar_module.GrammarModuleResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/grammar-modules")
@RequiredArgsConstructor
public class GrammarModuleController {

  private final GrammarModuleService grammarModuleService;

  @GetMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ResponseDTO<GrammarModuleResponseDTO>> readById(@PathVariable UUID id) {
    ResponseDTO<GrammarModuleResponseDTO> response = grammarModuleService.readById(id);
    return ResponseEntity.status(response.status()).body(response);
  }

  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ResponseDTO<List<GrammarModuleResponseItemDTO>>> readAll() {
    ResponseDTO<List<GrammarModuleResponseItemDTO>> response = grammarModuleService.readAll();
    return ResponseEntity.status(response.status()).body(response);
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ResponseDTO<GrammarModuleResponseDTO>> create(
      @Valid @RequestBody GrammarModuleCreateDTO dto) {
    ResponseDTO<GrammarModuleResponseDTO> response = grammarModuleService.create(dto);
    return ResponseEntity.status(response.status()).body(response);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ResponseDTO<GrammarModuleResponseDTO>> update(
      @PathVariable UUID id,
      @Valid @RequestBody GrammarModuleUpdateDTO dto) {
    ResponseDTO<GrammarModuleResponseDTO> response = grammarModuleService.update(id, dto);
    return ResponseEntity.status(response.status()).body(response);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable UUID id) {
    ResponseDTO<Void> response = grammarModuleService.delete(id);
    return ResponseEntity.status(response.status()).body(response);
  }
}

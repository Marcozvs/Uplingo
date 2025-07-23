package com.uplingo.uplingo_resource_server.ui.controllers;

import com.uplingo.uplingo_resource_server.application.services.CourseService;
import com.uplingo.uplingo_resource_server.ui.dtos.course.CourseCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.course.CourseUpdateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.course.CourseResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.course.CourseResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

  private final CourseService courseService;

  @GetMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ResponseDTO<CourseResponseDTO>> readById(@PathVariable UUID id) {
    ResponseDTO<CourseResponseDTO> response = courseService.readById(id);
    return ResponseEntity.status(response.status()).body(response);
  }

  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ResponseDTO<List<CourseResponseItemDTO>>> readAll() {
    ResponseDTO<List<CourseResponseItemDTO>> response = courseService.readAll();
    return ResponseEntity.status(response.status()).body(response);
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ResponseDTO<CourseResponseDTO>> create(@Valid @RequestBody CourseCreateDTO dto) {
    ResponseDTO<CourseResponseDTO> response = courseService.create(dto);
    return ResponseEntity.status(response.status()).body(response);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ResponseDTO<CourseResponseDTO>> update(
      @PathVariable UUID id,
      @Valid @RequestBody CourseUpdateDTO dto
  ) {
    ResponseDTO<CourseResponseDTO> response = courseService.update(id, dto);
    return ResponseEntity.status(response.status()).body(response);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable UUID id) {
    ResponseDTO<Void> response = courseService.delete(id);
    return ResponseEntity.status(response.status()).body(response);
  }
}

package com.uplingo.uplingo_resource_server.application.services;

import com.uplingo.uplingo_resource_server.application.utils.AuthUtils;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.CourseRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.GrammarModuleRepository;
import com.uplingo.uplingo_resource_server.model.entities.GrammarModule;
import com.uplingo.uplingo_resource_server.ui.dtos.grammar_module.*;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;
import com.uplingo.uplingo_resource_server.ui.mappers.GrammarModuleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GrammarModuleService {

  private final GrammarModuleRepository grammarModuleRepository;
  private final CourseRepository courseRepository;
  private final GrammarModuleMapper grammarModuleMapper;

  @Transactional
  public ResponseDTO<GrammarModuleResponseDTO> create(GrammarModuleCreateDTO dto) {
    boolean courseExists = courseRepository.existsById(dto.courseId());
    if (!courseExists) {
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Course not found");
    }

    GrammarModule module = grammarModuleMapper.toEntity(dto);

    UUID userLoggedId = AuthUtils.getUserId();
    module.setCreatedBy(userLoggedId);

    GrammarModule saved = grammarModuleRepository.save(module);

    return ResponseDTO.success(grammarModuleMapper.toResponseDto(saved), "Module created");
  }

  @Transactional(readOnly = true)
  public ResponseDTO<GrammarModuleResponseDTO> readById(UUID id) {
    Optional<GrammarModule> opt = grammarModuleRepository.findById(id);
    if (opt.isEmpty())
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Module not found");

    return ResponseDTO.success(grammarModuleMapper.toResponseDto(opt.get()));
  }

  @Transactional(readOnly = true)
  public ResponseDTO<List<GrammarModuleResponseItemDTO>> readAll() {
    List<GrammarModule> modules = grammarModuleRepository.findAll();
    List<GrammarModuleResponseItemDTO> dtoList = grammarModuleMapper.toResponseItemDtoList(modules);
    return ResponseDTO.success(dtoList);
  }

  @Transactional
  public ResponseDTO<GrammarModuleResponseDTO> update(UUID id, GrammarModuleUpdateDTO dto) {
    Optional<GrammarModule> opt = grammarModuleRepository.findById(id);
    if (opt.isEmpty())
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Module not found");

    GrammarModule module = opt.get();
    UUID requesterId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    if (!isAdmin && !requesterId.equals(module.getCreatedBy())) {
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed");
    }

    grammarModuleMapper.updateEntityFromDto(dto, module);
    module.setUpdatedBy(requesterId);

    GrammarModule updated = grammarModuleRepository.save(module);

    return ResponseDTO.success(grammarModuleMapper.toResponseDto(updated), "Module updated");
  }

  @Transactional
  public ResponseDTO<Void> delete(UUID id) {
    Optional<GrammarModule> opt = grammarModuleRepository.findById(id);
    if (opt.isEmpty())
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Module not found");

    GrammarModule module = opt.get();
    UUID requesterId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    if (!isAdmin && !requesterId.equals(module.getCreatedBy())) {
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed");
    }

    grammarModuleRepository.deleteById(id);
    return ResponseDTO.success(null, "Module deleted");
  }
}

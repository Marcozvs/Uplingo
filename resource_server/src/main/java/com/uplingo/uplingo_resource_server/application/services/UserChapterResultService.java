package com.uplingo.uplingo_resource_server.application.services;

import com.uplingo.uplingo_resource_server.application.utils.AuthUtils;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.UserChapterResultRepository;
import com.uplingo.uplingo_resource_server.model.entities.UserChapterResult;
import com.uplingo.uplingo_resource_server.ui.dtos.user_chapter_result.UserChapterResultCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_chapter_result.UserChapterResultResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_chapter_result.UserChapterResultResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_chapter_result.UserChapterResultUpdateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;
import com.uplingo.uplingo_resource_server.ui.mappers.UserChapterResultMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserChapterResultService {

  private final UserChapterResultRepository userChapterResultRepository;
  private final UserChapterResultMapper mapper;

  @Transactional
  public ResponseDTO<UserChapterResultResponseDTO> create(UserChapterResultCreateDTO dto) {
    UUID requesterId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    if (!isAdmin && !dto.userId().equals(requesterId)) {
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed to create results for other users");
    }

    UserChapterResult entity = mapper.toEntity(dto);
    entity.setId(UUID.randomUUID());
    entity.setCreatedBy(requesterId);
    entity.setUpdatedBy(requesterId);
    entity.setCreatedAt(LocalDateTime.now());

    UserChapterResult saved = userChapterResultRepository.save(entity);
    UserChapterResultResponseDTO responseDto = mapper.toResponseDto(saved);

    return ResponseDTO.success(responseDto, "UserChapterResult created");
  }

  @Transactional(readOnly = true)
  public ResponseDTO<UserChapterResultResponseDTO> readById(UUID id) {
    Optional<UserChapterResult> opt = userChapterResultRepository.findById(id);
    if (opt.isEmpty()) {
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "UserChapterResult not found");
    }

    UserChapterResult entity = opt.get();
    UUID requesterId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    if (!isAdmin && !entity.getUserId().equals(requesterId)) {
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed to read this result");
    }

    UserChapterResultResponseDTO dto = mapper.toResponseDto(entity);
    return ResponseDTO.success(dto);
  }

  @Transactional(readOnly = true)
  public ResponseDTO<List<UserChapterResultResponseItemDTO>> readAll() {
    UUID requesterId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    List<UserChapterResult> list;
    if (isAdmin) {
      list = userChapterResultRepository.findAll();
    } else {
      list = userChapterResultRepository.findByUserId(requesterId);
    }

    List<UserChapterResultResponseItemDTO> dtoList = mapper.toResponseItemDtoList(list);
    return ResponseDTO.success(dtoList);
  }

  @Transactional
  public ResponseDTO<UserChapterResultResponseDTO> update(UUID id, UserChapterResultUpdateDTO dto) {
    UUID requesterId = AuthUtils.getUserId();

    Optional<UserChapterResult> opt = userChapterResultRepository.findById(id);
    if (opt.isEmpty()) {
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "UserChapterResult not found");
    }

    UserChapterResult entity = opt.get();

    boolean isAdmin = AuthUtils.isAdmin();
    if (!isAdmin && !entity.getUserId().equals(requesterId)) {
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed to update this result");
    }

    mapper.updateEntityFromDto(dto, entity);
    entity.setUpdatedBy(requesterId);
    entity.setUpdatedAt(LocalDateTime.now());

    UserChapterResult updated = userChapterResultRepository.save(entity);
    UserChapterResultResponseDTO responseDto = mapper.toResponseDto(updated);

    return ResponseDTO.success(responseDto, "UserChapterResult updated");
  }

  @Transactional
  public ResponseDTO<Void> delete(UUID id) {
    Optional<UserChapterResult> opt = userChapterResultRepository.findById(id);
    if (opt.isEmpty()) {
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "UserChapterResult not found");
    }

    UserChapterResult entity = opt.get();
    UUID requesterId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    if (!isAdmin && !entity.getUserId().equals(requesterId)) {
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed to delete this result");
    }

    userChapterResultRepository.deleteById(id);
    return ResponseDTO.success(null, "UserChapterResult deleted");
  }
}

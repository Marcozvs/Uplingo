package com.uplingo.uplingo_resource_server.application.services;

import com.uplingo.uplingo_resource_server.application.utils.AuthUtils;
import com.uplingo.uplingo_resource_server.application.validations.UserHobbyValidation;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.AppUserRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.UserHobbyRepository;
import com.uplingo.uplingo_resource_server.model.entities.UserHobby;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_hobby.UserHobbyCreateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_hobby.UserHobbyResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_hobby.UserHobbyResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_hobby.UserHobbyUpdateDTO;
import com.uplingo.uplingo_resource_server.ui.mappers.UserHobbyMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserHobbyService {

  private final UserHobbyRepository userHobbyRepository;
  private final AppUserRepository appUserRepository;
  private final UserHobbyMapper userHobbyMapper;

  @Transactional
  public ResponseDTO<UserHobbyResponseDTO> create(UserHobbyCreateDTO dto) {
    ResponseDTO<Void> validation = UserHobbyValidation.validateCreateDTO(dto);
    if (validation != null)
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, validation.message());

    UUID requesterId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    if (!isAdmin && !requesterId.equals(dto.userId()))
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed");

    boolean userExists = appUserRepository.existsById(dto.userId());
    if (!userExists)
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "User not found");

    long count = userHobbyRepository.findAll().stream()
        .filter(h -> h.getUserId().equals(dto.userId()))
        .count();

    if (count >= 5)
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Maximum number of hobbies reached (5)");

    UserHobby hobby = userHobbyMapper.toEntity(dto);

    hobby.setCreatedBy(requesterId);

    UserHobby saved = userHobbyRepository.save(hobby);

    return ResponseDTO.success(userHobbyMapper.toResponseDto(saved), "Hobby created");
  }

  @Transactional(readOnly = true)
  public ResponseDTO<UserHobbyResponseDTO> readById(UUID id) {
    Optional<UserHobby> opt = userHobbyRepository.findById(id);
    if (opt.isEmpty())
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Hobby not found");

    UserHobby hobby = opt.get();
    UUID requesterId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    if (!isAdmin && !requesterId.equals(hobby.getUserId()))
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed");

    return ResponseDTO.success(userHobbyMapper.toResponseDto(hobby));
  }

  @Transactional(readOnly = true)
  public ResponseDTO<List<UserHobbyResponseItemDTO>> readAll() {
    UUID requesterId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    List<UserHobby> hobbies;

    if (isAdmin) {
      hobbies = userHobbyRepository.findAll();
    } else {
      hobbies = userHobbyRepository.findAll().stream()
          .filter(h -> h.getUserId().equals(requesterId))
          .toList();
    }

    return ResponseDTO.success(userHobbyMapper.toResponseItemDtoList(hobbies));
  }

  @Transactional
  public ResponseDTO<UserHobbyResponseDTO> update(UUID id, UserHobbyUpdateDTO dto) {
    var validation = UserHobbyValidation.validateUpdateDTO(dto);
    if (validation != null)
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, validation.message());

    Optional<UserHobby> opt = userHobbyRepository.findById(id);
    if (opt.isEmpty())
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Hobby not found");

    UserHobby hobby = opt.get();
    UUID requesterId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    if (!isAdmin && !requesterId.equals(hobby.getUserId()))
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed");

    userHobbyMapper.updateEntityFromDto(dto, hobby);
    hobby.setUpdatedBy(requesterId);

    userHobbyMapper.updateEntityFromDto(dto, hobby);
    UserHobby updated = userHobbyRepository.save(hobby);

    return ResponseDTO.success(userHobbyMapper.toResponseDto(updated), "Hobby updated");
  }

  @Transactional
  public ResponseDTO<Void> delete(UUID id) {
    Optional<UserHobby> opt = userHobbyRepository.findById(id);
    if (opt.isEmpty())
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Hobby not found");

    UserHobby hobby = opt.get();
    UUID requesterId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    if (!isAdmin && !requesterId.equals(hobby.getUserId()))
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed");

    userHobbyRepository.deleteById(id);
    return ResponseDTO.success(null, "Hobby deleted");
  }
}

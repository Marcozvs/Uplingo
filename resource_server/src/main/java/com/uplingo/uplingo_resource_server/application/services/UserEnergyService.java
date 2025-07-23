package com.uplingo.uplingo_resource_server.application.services;

import com.uplingo.uplingo_resource_server.application.utils.AuthUtils;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.UserEnergyRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.AppUserRepository;
import com.uplingo.uplingo_resource_server.model.entities.AppUser;
import com.uplingo.uplingo_resource_server.model.entities.UserEnergy;
import com.uplingo.uplingo_resource_server.model.enums.LanguageEnum;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_energy.*;
import com.uplingo.uplingo_resource_server.ui.mappers.UserEnergyMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserEnergyService {

  private final UserEnergyRepository userEnergyRepository;
  private final AppUserRepository appUserRepository;
  private final EmailService emailService;
  private final UserEnergyMapper userEnergyMapper;

  @Transactional
  public ResponseDTO<UserEnergyResponseDTO> create(UserEnergyCreateDTO dto) {
    boolean userExists = appUserRepository.existsById(dto.userId());
    if (!userExists)
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "User not found");

    boolean hasAvailableEnergy = userEnergyRepository.findAll().stream()
        .anyMatch(e -> e.getUserId().equals(dto.userId()) && !e.isConsumed());

    if (hasAvailableEnergy) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "User already has available energy");
    }

    UserEnergy entity = userEnergyMapper.toEntity(dto);

    UUID userLoggedId = AuthUtils.getUserId();

    entity.setCreatedBy(userLoggedId);
    entity.setConsumed(false);

    UserEnergy saved = userEnergyRepository.save(entity);

    var userOpt = appUserRepository.findById(dto.userId());
    if (userOpt.isPresent()) {
      AppUser user = userOpt.get();
      String email = user.getEmail();
      LanguageEnum language = user.getUserLanguage();
      String name = user.getName();

      String subject;
      String body;

      switch (language != null ? language : LanguageEnum.ENGLISH) {
        case SPANISH -> {
          subject = "¡Has recibido energía!";
          body = "Hola " + (name != null ? name : "usuario") + ",\n\n"
              + "Has recibido una unidad de energía para continuar con tu aprendizaje.\n\n"
              + "¡Sigue así!";
        }
        case FRENCH -> {
          subject = "Vous avez reçu de l'énergie !";
          body = "Bonjour " + (name != null ? name : "utilisateur") + ",\n\n"
              + "Vous avez reçu une unité d'énergie pour continuer votre apprentissage.\n\n"
              + "Continuez comme ça !";
        }
        case PORTUGUESE_BR -> {
          subject = "Você recebeu energia!";
          body = "Olá " + (name != null ? name : "usuário") + ",\n\n"
              + "Você recebeu uma unidade de energia para continuar seu aprendizado.\n\n"
              + "Continue assim!";
        }
        default -> {
          subject = "You have received energy!";
          body = "Hello " + (name != null ? name : "user") + ",\n\n"
              + "You have received one unit of energy to continue your learning journey.\n\n"
              + "Keep it up!";
        }
      }

      emailService.sendMail(email, subject, body);
    }

    return ResponseDTO.success(userEnergyMapper.toResponseDto(saved), "Energy record created");
  }

  @Transactional(readOnly = true)
  public ResponseDTO<UserEnergyResponseDTO> readById(UUID id) {
    Optional<UserEnergy> opt = userEnergyRepository.findById(id);
    if (opt.isEmpty())
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Energy record not found");

    UserEnergy energy = opt.get();
    UUID userLoggedId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    if (!isAdmin && !userLoggedId.equals(energy.getUserId()))
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed");

    return ResponseDTO.success(userEnergyMapper.toResponseDto(energy));
  }

  @Transactional(readOnly = true)
  public ResponseDTO<List<UserEnergyResponseItemDTO>> readAll(UUID userId) {
    UUID userLoggedId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    if (!isAdmin && !userLoggedId.equals(userId))
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed");

    List<UserEnergy> energies = userEnergyRepository.findAll().stream()
        .filter(e -> e.getUserId().equals(userId))
        .toList();

    return ResponseDTO.success(userEnergyMapper.toResponseItemDtoList(energies));
  }

  @Transactional
  public ResponseDTO<UserEnergyResponseDTO> update(UUID id, UserEnergyUpdateDTO dto) {
    Optional<UserEnergy> opt = userEnergyRepository.findById(id);
    if (opt.isEmpty())
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Energy record not found");

    UserEnergy energy = opt.get();
    UUID userLoggedId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    if (!isAdmin && !userLoggedId.equals(energy.getUserId()))
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed");

    userEnergyMapper.updateEntityFromDto(dto, energy);
    energy.setUpdatedBy(userLoggedId);

    userEnergyMapper.updateEntityFromDto(dto, energy);
    UserEnergy updated = userEnergyRepository.save(energy);

    return ResponseDTO.success(userEnergyMapper.toResponseDto(updated), "Energy record updated");
  }

  @Transactional
  public ResponseDTO<Void> delete(UUID id) {
    Optional<UserEnergy> opt = userEnergyRepository.findById(id);
    if (opt.isEmpty())
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "Energy record not found");

    UserEnergy energy = opt.get();
    UUID userLoggedId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    if (!isAdmin && !userLoggedId.equals(energy.getUserId()))
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed");

    userEnergyRepository.deleteById(id);
    return ResponseDTO.success(null, "Energy record deleted");
  }

  @Transactional
  public ResponseDTO<Void> requestEnergy() {
    UUID requesterId = AuthUtils.getUserId();
    Optional<AppUser> userOpt = appUserRepository.findById(requesterId);
    if (userOpt.isEmpty())
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "User not found");

    AppUser user = userOpt.get();

    String subject = "Energy Request from User";
    String body = String.format(
        "User %s (ID: %s, Email: %s) has requested energy.",
        user.getName() != null ? user.getName() : "Unknown",
        user.getId(),
        user.getEmail());

    emailService.sendMail("uplingo.dev@gmail.com", subject, body);

    return ResponseDTO.success(null, "Energy request sent to administrator");
  }
}

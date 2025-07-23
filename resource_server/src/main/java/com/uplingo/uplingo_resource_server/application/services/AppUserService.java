package com.uplingo.uplingo_resource_server.application.services;

import com.uplingo.uplingo_resource_server.application.utils.AuthUtils;
import com.uplingo.uplingo_resource_server.application.validations.AppUserValidations;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.AppUserRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.UserChapterResultRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.UserEnergyRepository;
import com.uplingo.uplingo_resource_server.infrastructure.repositories.UserHobbyRepository;
import com.uplingo.uplingo_resource_server.model.entities.AppUser;
import com.uplingo.uplingo_resource_server.model.entities.UserHobby;
import com.uplingo.uplingo_resource_server.model.enums.LanguageEnum;
import com.uplingo.uplingo_resource_server.ui.dtos.app_user.AppUserResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.app_user.AppUserResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.app_user.AppUserUpdateDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.response.ResponseDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.user_hobby.UserHobbyResponseItemDTO;
import com.uplingo.uplingo_resource_server.ui.mappers.AppUserMapper;
import com.uplingo.uplingo_resource_server.ui.mappers.UserHobbyMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppUserService {

  private final AppUserRepository appUserRepository;
  private final UserHobbyRepository userHobbyRepository;
  private final UserEnergyRepository userEnergyRepository;
  private final UserChapterResultRepository userChapterResultRepository;
  private final AppUserMapper appUserMapper;
  private final UserHobbyMapper userHobbyMapper;
  private final EmailService emailService;

  @Transactional(readOnly = true)
  public ResponseDTO<AppUserResponseDTO> readById(UUID targetId) {
    UUID requesterId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    Optional<AppUser> userOpt = appUserRepository.findById(targetId);
    if (userOpt.isEmpty())
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "User not found");

    AppUser user = userOpt.get();

    if (user.isBanned())
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Account is banned");

    if (!isAdmin && !requesterId.equals(targetId))
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed");

    List<UserHobby> hobbies = userHobbyRepository.findAll().stream()
        .filter(h -> h.getUserId().equals(targetId))
        .toList();

    List<UserHobbyResponseItemDTO> hobbyDtos = userHobbyMapper.toResponseItemDtoList(hobbies);

    Integer energy = userEnergyRepository.countByUserIdAndConsumedFalseAndValidUntilAfter(targetId,
        LocalDateTime.now());

    Integer totalXp = userChapterResultRepository.sumXpEarnedByUserId(targetId);

    return ResponseDTO.success(
        appUserMapper.toResponseDto(user, hobbyDtos, energy, totalXp));
  }

  @Transactional(readOnly = true)
  public ResponseDTO<List<AppUserResponseItemDTO>> readAll() {
    List<AppUser> users = appUserRepository.findAll().stream()
        .filter(u -> !u.isBanned())
        .toList();

    return ResponseDTO.success(appUserMapper.toResponseItemDtoList(users));
  }

  @Transactional
  public ResponseDTO<AppUserResponseDTO> update(UUID targetId, AppUserUpdateDTO dto) {
    UUID requesterId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    ResponseDTO<Void> validation = AppUserValidations.validateUpdateDTO(dto);
    if (validation != null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, validation.message());
    }

    Optional<AppUser> opt = appUserRepository.findById(targetId);
    if (opt.isEmpty()) {
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "User not found");
    }

    AppUser user = opt.get();
    if (user.isBanned()) {
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Account is banned");
    }

    if (!isAdmin && !requesterId.equals(targetId)) {
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed");
    }

    appUserMapper.updateEntityFromDto(dto, user);
    user.setUpdatedAt(LocalDateTime.now());
    user.setUpdatedBy(requesterId);

    AppUser updated = appUserRepository.save(user);

    List<UserHobby> hobbies = userHobbyRepository.findAll().stream()
        .filter(h -> h.getUserId().equals(targetId))
        .toList();

    List<UserHobbyResponseItemDTO> hobbyDtos = userHobbyMapper.toResponseItemDtoList(hobbies);

    Integer energy = userEnergyRepository.countByUserIdAndConsumedFalseAndValidUntilAfter(targetId,
        LocalDateTime.now());
    Integer totalXp = userChapterResultRepository.sumXpEarnedByUserId(targetId);

    return ResponseDTO.success(appUserMapper.toResponseDto(updated, hobbyDtos, energy, totalXp), "User updated");
  }

  @Transactional
  public ResponseDTO<Void> delete(UUID targetId) {
    UUID requesterId = AuthUtils.getUserId();
    boolean isAdmin = AuthUtils.isAdmin();

    Optional<AppUser> opt = appUserRepository.findById(targetId);
    if (opt.isEmpty()) {
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "User not found");
    }

    AppUser user = opt.get();
    if (user.isBanned()) {
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Account is banned");
    }

    if (!isAdmin && !requesterId.equals(targetId)) {
      return ResponseDTO.error(HttpStatus.FORBIDDEN, "Not allowed");
    }

    String name = user.getName();
    String email = user.getEmail();
    LanguageEnum language = user.getUserLanguage();

    String subject;
    String body;

    switch (language != null ? language : LanguageEnum.ENGLISH) {
      case SPANISH -> {
        subject = "Cuenta eliminada";
        body = "Hola " + (name != null ? name : "usuario") + ",\n\n"
            + "Tu cuenta ha sido eliminada de forma permanente del sistema. "
            + "Lamentamos verte partir. Si fue por erro o tienes dudas, contacta con nuestro equipo de soporte.\n\n"
            + "Atentamente,\nEquipo de Uplingo";
      }
      case FRENCH -> {
        subject = "Compte supprimé";
        body = "Bonjour " + (name != null ? name : "utilisateur") + ",\n\n"
            + "Votre compte a été définitivement supprimé de notre système. "
            + "Nous sommes désolés de vous voir partir. Si cela est une erreur, contactez notre support.\n\n"
            + "Cordialement,\nL’équipe Uplingo";
      }
      case PORTUGUESE_BR -> {
        subject = "Conta removida";
        body = "Olá " + (name != null ? name : "usuário") + ",\n\n"
            + "Sua conta foi removida permanentemente do sistema. "
            + "Sentimos muito em ver você partir. Se isso foi um engano, entre em contato com nosso suporte.\n\n"
            + "Atenciosamente,\nEquipe Uplingo";
      }
      default -> {
        subject = "Account deleted";
        body = "Hello " + (name != null ? name : "user") + ",\n\n"
            + "Your account has been permanently deleted from our system. "
            + "We’re sorry to see you go. If this was a mistake, contact our support team.\n\n"
            + "Sincerely,\nUplingo Team";
      }
    }

    appUserRepository.deleteById(targetId);
    emailService.sendMail(email, subject, body);

    return ResponseDTO.success(null, "User deleted and notified");
  }

  @Transactional
  public ResponseDTO<Void> ban(UUID targetId) {
    UUID requesterId = AuthUtils.getUserId();

    Optional<AppUser> opt = appUserRepository.findById(targetId);
    if (opt.isEmpty()) {
      return ResponseDTO.error(HttpStatus.NOT_FOUND, "User not found");
    }

    AppUser user = opt.get();
    if (user.isBanned()) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "User already banned");
    }

    userHobbyRepository.deleteAllByUserId(targetId);

    String name = user.getName();
    String email = user.getEmail();
    LocalDateTime bannedAt = LocalDateTime.now();
    LanguageEnum userLanguage = user.getUserLanguage();

    user.setBanned(true);
    user.setBannedAt(bannedAt);
    user.setBannedBy(requesterId);

    user.setName("Banned User");
    user.setPassword("");
    user.setDescription("");
    user.setReasonsLearn("");
    user.setUserLanguage(LanguageEnum.ENGLISH);
    user.setPhotoUrl("");

    appUserRepository.save(user);

    Locale locale;
    switch (userLanguage) {
      case ENGLISH -> locale = Locale.ENGLISH;
      case SPANISH -> locale = Locale.of("sp", "SP");
      case FRENCH -> locale = Locale.FRENCH;
      case PORTUGUESE_BR -> locale = Locale.of("pt", "BR");
      default -> locale = Locale.ENGLISH;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy 'at' HH:mm", locale);
    String formattedDate = bannedAt.format(formatter);

    String subject;
    String body;

    switch (userLanguage) {
      case ENGLISH -> {
        subject = "Account banned";
        body = "Hello " + (name != null ? name : "user") + ",\n\n"
            + "We inform you that your account has been banned on " + formattedDate + ". "
            + "All your personal data has been removed from our system according to our security policy.\n\n"
            + "If you believe this is a mistake or would like to appeal, please contact our support team.\n\n"
            + "Sincerely,\nUplingo Team";
      }
      case SPANISH -> {
        subject = "Cuenta suspendida";
        body = "Hola " + (name != null ? name : "usuario") + ",\n\n"
            + "Te informamos que tu cuenta fue suspendida el " + formattedDate + ". "
            + "Todos tus datos personales han sido eliminados de nuestro sistema según nuestra política de seguridad.\n\n"
            + "Si crees que esto fue un error o deseas apelar, contacta con nuestro equipo de soporte.\n\n"
            + "Atentamente,\nEquipo de Uplingo";
      }
      case FRENCH -> {
        subject = "Compte banni";
        body = "Bonjour " + (name != null ? name : "utilisateur") + ",\n\n"
            + "Nous vous informons que votre compte a été banni le " + formattedDate + ". "
            + "Toutes vos données personnelles ont été supprimées de notre système conformément à notre politique de sécurité.\n\n"
            + "Si vous pensez qu’il s’agit d’une erreur ou souhaitez faire appel, veuillez contacter notre support.\n\n"
            + "Cordialement,\nL’équipe Uplingo";
      }
      case PORTUGUESE_BR -> {
        subject = "Conta banida";
        body = "Olá " + (name != null ? name : "usuário") + ",\n\n"
            + "Informamos que sua conta foi banida em " + formattedDate + ". "
            + "Todos os seus dados pessoais foram removidos do nosso sistema conforme nossa política de segurança.\n\n"
            + "Se você acredita que isso foi um engano ou deseja contestar a decisão, entre em contato com nosso suporte.\n\n"
            + "Atenciosamente,\nEquipe Uplingo";
      }
      default -> {
        subject = "Account banned";
        body = "Hello " + (name != null ? name : "user") + ",\n\n"
            + "We inform you that your account has been banned on " + formattedDate + ". "
            + "All your personal data has been removed from our system according to our security policy.\n\n"
            + "If you believe this is a mistake or would like to appeal, please contact our support team.\n\n"
            + "Sincerely,\nUplingo Team";
      }
    }

    emailService.sendMail(email, subject, body);

    return ResponseDTO.success(null, "User banned and notified");
  }
}

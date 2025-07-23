package com.uplingo.uplingo_authorization_server.application.services;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uplingo.uplingo_authorization_server.application.exceptions.BadRequestException;
import com.uplingo.uplingo_authorization_server.application.validations.AppUserValidations;
import com.uplingo.uplingo_authorization_server.infrastructure.repositories.AppUserRepository;
import com.uplingo.uplingo_authorization_server.model.entities.AppUser;
import com.uplingo.uplingo_authorization_server.ui.dtos.response.ResponseDTO;
import com.uplingo.uplingo_authorization_server.ui.dtos.user.AppUserCreateDTO;

@Service
@RequiredArgsConstructor
public class AppUserService {

  private final AppUserRepository appUserRepository;
  private final EmailService emailService;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public ResponseDTO<AppUser> create(AppUserCreateDTO dto) {
    ResponseDTO<Void> validationResult = AppUserValidations.validateCreateDTO(dto, appUserRepository);
    if (validationResult != null) {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, validationResult.message());
    }

    AppUser newAppUser = AppUser.builder()
        .name(dto.name())
        .email(dto.email())
        .password(passwordEncoder.encode(dto.password()))
        .roles(dto.roles())
        .banned(dto.banned())
        .description(null)
        .reasonsLearn(null)
        .userLanguage(dto.userLanguage())
        .photoUrl(dto.photoUrl())
        .firstTime(dto.firstTime())
        .build();

    appUserRepository.save(newAppUser);

    Optional<AppUser> appUser = readByEmail(newAppUser.getEmail());

    if (appUser.isPresent()) {
      String subject = "Welcome to Uplingo";
      String message = "Hello " + appUser.get().getName() + ",\n\n" +
          "Thank you for registering on Uplingo.\n\n" +
          "Please note: this is a portfolio project created for study and demonstration purposes only.\n" +
          "Feel free to explore the features and test the platform.\n\n" +
          "Best regards,\n" +
          "Marcos";

      emailService.sendMail(appUser.get().getEmail(), subject, message);

      return ResponseDTO.created("User successfully created", appUser.get());
    } else {
      return ResponseDTO.error(HttpStatus.BAD_REQUEST, "Error when trying to register user");
    }
  }

  @Transactional(readOnly = true)
  public Optional<AppUser> readByEmail(String email) {
    if (email == null || email.isBlank()) {
      throw new BadRequestException("Email must not be null or blank");
    }
    return appUserRepository.findByEmail(email);
  }
}

package com.uplingo.uplingo_authorization_server.infrastructure.configurations.security;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.uplingo.uplingo_authorization_server.application.services.AppUserService;
import com.uplingo.uplingo_authorization_server.model.entities.AppUser;
import com.uplingo.uplingo_authorization_server.model.enums.LanguageEnum;
import com.uplingo.uplingo_authorization_server.model.enums.RoleEnum;
import com.uplingo.uplingo_authorization_server.ui.dtos.user.AppUserCreateDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GoogleSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
  private final AppUserService appUserService;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication) throws ServletException, IOException {

    OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
    OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();

    String name = oAuth2User.getAttribute("name");
    String email = oAuth2User.getAttribute("email");
    String photoUrl = oAuth2User.getAttribute("picture");

    if (photoUrl == null || photoUrl.isBlank()) {
      photoUrl = "none";
    }

    Optional<AppUser> optionalAppUser = appUserService.readByEmail(email);

    AppUser appUser;
    if (optionalAppUser.isPresent()) {
      appUser = optionalAppUser.get();
    } else {
      AppUserCreateDTO newAppUser = new AppUserCreateDTO(
          name,
          email,
          UUID.randomUUID().toString(),
          List.of(RoleEnum.STUDENT.toString()),
          LanguageEnum.ENGLISH,
          photoUrl,
          false,
          true);

      appUserService.create(newAppUser);
      appUser = appUserService.readByEmail(email).get();
    }

    CustomAuthentication customAuthentication = new CustomAuthentication(
        appUser,
        appUser.getRoles().stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList()));

    SecurityContextHolder.getContext().setAuthentication(customAuthentication);

    super.onAuthenticationSuccess(request, response, customAuthentication);
  }

}

package com.uplingo.uplingo_authorization_server.application.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.uplingo.uplingo_authorization_server.model.entities.AppUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final AppUserService appUserService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    AppUser appUser = appUserService.readByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    return User.builder()
        .username(appUser.getEmail())
        .password(appUser.getPassword())
        .roles(appUser.getRoles().toArray(new String[0]))
        .build();
  }
}

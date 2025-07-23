package com.uplingo.uplingo_resource_server.application.utils;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {

  public static UUID getUserId() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
      throw new SecurityException("Unauthorized");
    }
    return UUID.fromString(auth.getName());
  }

  public static boolean isAdmin() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
      return false;
    }
    return auth.getAuthorities().stream()
      .anyMatch(granted -> granted.getAuthority().equals("ROLE_ADMIN"));
  }
}

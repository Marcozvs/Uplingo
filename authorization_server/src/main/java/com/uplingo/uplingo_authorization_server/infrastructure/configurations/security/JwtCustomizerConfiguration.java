package com.uplingo.uplingo_authorization_server.infrastructure.configurations.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import com.uplingo.uplingo_authorization_server.model.entities.AppUser;

@Configuration
public class JwtCustomizerConfiguration {
  @Bean
  public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
    return context -> {
      if (context.getPrincipal() instanceof CustomAuthentication customAuth) {
        AppUser user = customAuth.getPrincipal();

        context.getClaims().claim("sub", user.getId().toString());
        context.getClaims().claim("email", user.getEmail());
        context.getClaims().claim("name", user.getName());
        context.getClaims().claim("roles", user.getRoles());
      }
    };
  }

}

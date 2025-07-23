package com.uplingo.uplingo_resource_server.infrastructure.configurations.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class TokenConfiguration {

  @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
  private String jwkSetUri;

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

    converter.setJwtGrantedAuthoritiesConverter(jwt -> {
      List<String> roles = (List<String>) jwt.getClaims().get("roles");
      if (roles == null) {
        return List.of();
      }

      return roles.stream()
          .map(role -> "ROLE_" + role.toUpperCase())
          .map(SimpleGrantedAuthority::new)
          .collect(Collectors.toList());
    });

    return converter;
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    System.out.println(jwkSetUri);
    System.out.println(NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build());
    return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
  }
}
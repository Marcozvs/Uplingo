package com.uplingo.uplingo_authorization_server.infrastructure.configurations.security;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class TokenConfiguration {
  @Bean
  public TokenSettings tokenSettings() {
    return TokenSettings
        .builder()
        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
        .accessTokenTimeToLive(Duration.ofHours(1))
        .build();
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource() throws Exception {
    RSAKey rsaKey = loadRsaKey();
    return new ImmutableJWKSet<>(new JWKSet(rsaKey));
  }

  private RSAKey loadRsaKey() throws Exception {
    String privateKeyPem = Files.readString(Paths.get("src/main/resources/keys/private.pem"));
    String publicKeyPem = Files.readString(Paths.get("src/main/resources/keys/public.pem"));

    RSAPrivateKey privateKey = PemUtils.parseRSAPrivateKey(privateKeyPem);
    RSAPublicKey publicKey = PemUtils.parseRSAPublicKey(publicKeyPem);

    return new RSAKey.Builder(publicKey)
        .privateKey(privateKey)
        .keyID("uplingo-key")
        .build();
  }

  @Bean
  JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
  }
}

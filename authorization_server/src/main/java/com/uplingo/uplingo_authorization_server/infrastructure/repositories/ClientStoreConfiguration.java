package com.uplingo.uplingo_authorization_server.infrastructure.repositories;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

@Configuration
public class ClientStoreConfiguration {
  @Bean
  public RegisteredClientRepository registeredClientRepository() {
    RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
        .clientId("uplingo_web_client_dev")
        .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .redirectUri("http://localhost:4205/login/callback")
        .scope(OidcScopes.OPENID)
        .scope(OidcScopes.PROFILE)
        .scope("email")
        .clientSettings(ClientSettings.builder()
            .requireProofKey(true)
            .build())
        .build();

    return new InMemoryRegisteredClientRepository(client);
  }
}
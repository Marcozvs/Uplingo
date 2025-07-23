package com.uplingo.uplingo_authorization_server.infrastructure.configurations.security;

import java.security.Principal;
import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.uplingo.uplingo_authorization_server.model.entities.AppUser;

import lombok.Getter;

@Getter
public class CustomAuthentication extends AbstractAuthenticationToken implements Principal {

  private final AppUser principal;

  public CustomAuthentication(AppUser principal, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public AppUser getPrincipal() {
    return principal;
  }

  @Override
  public String getName() {
    return principal.getName();
  }
}

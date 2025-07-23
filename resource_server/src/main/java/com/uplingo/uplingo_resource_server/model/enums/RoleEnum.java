package com.uplingo.uplingo_resource_server.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoleEnum {
  ADMIN("ADMIN"),
  STUDENT("STUDENT");

  private final String value;

  @Override
  public String toString() {
    return value;
  }
}

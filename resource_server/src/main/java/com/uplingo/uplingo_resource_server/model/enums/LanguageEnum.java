package com.uplingo.uplingo_resource_server.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LanguageEnum {
  ENGLISH("ENGLISH"),
  SPANISH("SPANISH"),
  FRENCH("FRENCH"),
  PORTUGUESE_BR("PORTUGUESE_BR");

  private final String value;

  @Override
  public String toString() {
    return value;
  }
}

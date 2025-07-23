package com.uplingo.uplingo_resource_server.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CharacterTypeEnum {
  MAIN("MAIN"),
  SUPPORTING("SUPPORTING");

  private final String value;

  @Override
  public String toString() {
    return value;
  }
}

package com.uplingo.uplingo_resource_server.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LevelEnum {
  A1("A1"),
  A2("A2"),
  B1("B1"),
  B2("B2"),
  C1("C1"),
  C2("C2");

  private final String value;

  @Override
  public String toString() {
    return value;
  }
}

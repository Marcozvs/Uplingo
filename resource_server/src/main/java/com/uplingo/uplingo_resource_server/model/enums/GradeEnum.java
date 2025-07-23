package com.uplingo.uplingo_resource_server.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum GradeEnum {
  A("A"),
  B("B"),
  C("C"),
  D("D"),
  F("F");

  private final String value;

  @Override
  public String toString() {
    return value;
  }
}

package com.uplingo.uplingo_resource_server.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NarratorTypeEnum {
  FIRST_PERSON("FIRST_PERSON"),
  SECOND_PERSON("SECOND_PERSON"),
  THIRD_PERSON("THIRD_PERSON");

  private final String value;

  @Override
  public String toString() {
    return value;
  }
}

package com.uplingo.uplingo_resource_server.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CourseNameEnum {
  ENGLISH_COURSE("ENGLISH_COURSE"),
  SPANISH_COURSE("SPANISH_COURSE"),
  FRENCH_COURSE("FRENCH_COURSE"),
  PORTUGUESE_BR_COURSE("PORTUGUESE_BR_COURSE");

  private final String value;

  @Override
  public String toString() {
    return value;
  }
}


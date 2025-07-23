package com.uplingo.uplingo_resource_server.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ChapterStatusEnum {
  IN_PROGRESS("IN_PROGRESS"),
  COMPLETED("COMPLETED");

  private final String value;

  @Override
  public String toString() {
    return value;
  }
}

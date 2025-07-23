package com.uplingo.uplingo_resource_server.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum GenreEnum {
  FANTASY("FANTASY"),
  SCI_FI("SCI_FI"),
  ROMANCE("ROMANCE"),
  MYSTERY("MYSTERY"),
  THRILLER("THRILLER"),
  HORROR("HORROR"),
  ADVENTURE("ADVENTURE"),
  HISTORICAL("HISTORICAL"),
  DRAMA("DRAMA"),
  COMEDY("COMEDY"),
  ACTION("ACTION"),
  CRIME("CRIME"),
  DETECTIVE("DETECTIVE"),
  BIOGRAPHY("BIOGRAPHY"),
  DYSTOPIAN("DYSTOPIAN"),
  PARANORMAL("PARANORMAL"),
  SUPERHERO("SUPERHERO"),
  FAIRY_TALE("FAIRY_TALE"),
  WESTERN("WESTERN"),
  SPOOKY("SPOOKY");

  private final String value;

  @Override
  public String toString() {
    return value;
  }
}

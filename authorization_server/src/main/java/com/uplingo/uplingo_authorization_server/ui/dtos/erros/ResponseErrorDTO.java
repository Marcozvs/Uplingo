package com.uplingo.uplingo_authorization_server.ui.dtos.erros;

import java.util.List;

import org.springframework.http.HttpStatus;

public record ResponseErrorDTO(
    int status,
    String message,
    List<InvalidFieldDTO> errors) {

  public static ResponseErrorDTO defaultError(String message) {
    return new ResponseErrorDTO(HttpStatus.BAD_REQUEST.value(), message, List.of());
  }

  public static ResponseErrorDTO conflictError(String message) {
    return new ResponseErrorDTO(HttpStatus.CONFLICT.value(), message, List.of());
  }

  public static ResponseErrorDTO notFoundError(String message) {
    return new ResponseErrorDTO(HttpStatus.NOT_FOUND.value(), message, List.of());
  }

  public static ResponseErrorDTO serverError(String message) {
    return new ResponseErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, List.of());
  }
}
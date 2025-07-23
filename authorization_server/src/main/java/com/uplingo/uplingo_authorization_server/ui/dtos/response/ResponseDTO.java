package com.uplingo.uplingo_authorization_server.ui.dtos.response;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public record ResponseDTO<T>(
    Integer status,
    String message,
    T data,
    LocalDateTime timestamp) {
  public ResponseDTO(HttpStatus status, String message, T data) {
    this(status.value(), message, data, LocalDateTime.now());
  }

  public static <T> ResponseDTO<T> success(T data, String message) {
    return new ResponseDTO<>(HttpStatus.OK, message, data);
  }

  public static <T> ResponseDTO<T> created(T data, String message) {
    return new ResponseDTO<>(HttpStatus.CREATED, message, data);
  }

  public static <T> ResponseDTO<T> created(String message, T data) {
    return new ResponseDTO<>(HttpStatus.CREATED, message, data);
  }

  public static <T> ResponseDTO<T> error(HttpStatus status, String message) {
    return new ResponseDTO<>(status, message, null);
  }
}

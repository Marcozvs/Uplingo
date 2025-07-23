package com.uplingo.uplingo_authorization_server.application.exceptions;

import com.uplingo.uplingo_authorization_server.ui.dtos.erros.InvalidFieldDTO;
import com.uplingo.uplingo_authorization_server.ui.dtos.erros.ResponseErrorDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ResponseErrorDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    List<InvalidFieldDTO> errors = ex.getFieldErrors().stream()
        .map(fieldError -> new InvalidFieldDTO(fieldError.getField(), fieldError.getDefaultMessage()))
        .collect(Collectors.toList());

    return new ResponseErrorDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error", errors);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseErrorDTO handleConstraintViolationException(ConstraintViolationException ex) {
    Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

    List<InvalidFieldDTO> errors = violations.stream()
        .map(v -> new InvalidFieldDTO(v.getPropertyPath().toString(), v.getMessage()))
        .collect(Collectors.toList());

    return new ResponseErrorDTO(HttpStatus.BAD_REQUEST.value(), "Validation error", errors);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseErrorDTO handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    return new ResponseErrorDTO(HttpStatus.BAD_REQUEST.value(), "Malformed JSON request", List.of());
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  public ResponseErrorDTO handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
    return new ResponseErrorDTO(HttpStatus.METHOD_NOT_ALLOWED.value(), "HTTP method not allowed", List.of());
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseErrorDTO handleAccessDeniedException(AccessDeniedException ex) {
    return new ResponseErrorDTO(HttpStatus.FORBIDDEN.value(), "Access denied", List.of());
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseErrorDTO handleNotFoundException(NotFoundException ex) {
    return ResponseErrorDTO.notFoundError(ex.getMessage());
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseErrorDTO handleBadRequestException(BadRequestException ex) {
    return ResponseErrorDTO.defaultError(ex.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseErrorDTO handleIllegalArgumentException(IllegalArgumentException ex) {
    return ResponseErrorDTO.defaultError("Invalid argument: " + ex.getMessage());
  }

  @ExceptionHandler(IllegalStateException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseErrorDTO handleIllegalStateException(IllegalStateException ex) {
    return ResponseErrorDTO.defaultError("Illegal state: " + ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseErrorDTO handleGenericException(Exception ex) {
    return ResponseErrorDTO.defaultError("Unexpected error occurred: " + ex.getMessage());
  }
}

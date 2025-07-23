package com.uplingo.uplingo_resource_server.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.uplingo.uplingo_resource_server.ui.dtos.errors.InvalidFieldDTO;
import com.uplingo.uplingo_resource_server.ui.dtos.errors.ResponseErrorDTO;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ResponseErrorDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException error) {
    List<FieldError> fieldErrors = error.getFieldErrors();

    List<InvalidFieldDTO> InvalidFieldDTOs = fieldErrors
        .stream()
        .map(fieldError -> new InvalidFieldDTO(fieldError.getField(), fieldError.getDefaultMessage()))
        .collect(Collectors.toList());

    return new ResponseErrorDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error", InvalidFieldDTOs);
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseErrorDTO handleAccessDeniedException(AccessDeniedException accessDeniedException) {
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

}

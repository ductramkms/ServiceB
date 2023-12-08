package com.example.service_b.exception.custom;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidDataException extends Exception {

  public InvalidDataException(String message) {
    super(message);
  }
}

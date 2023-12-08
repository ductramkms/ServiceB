package com.example.service_b.exception.custom;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemAlreadyExistsException extends RuntimeException {

  public ItemAlreadyExistsException(String message) {
    super(message);
  }
}

package com.example.ServiceB.exception.custom;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidDataException extends Exception{
  public  InvalidDataException(String message) {
    super(message);
  }
}

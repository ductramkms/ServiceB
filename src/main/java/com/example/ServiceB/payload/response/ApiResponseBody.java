package com.example.ServiceB.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
@AllArgsConstructor
public class ApiResponseBody {

  HttpStatus status;
  String message;
  Object data;
}

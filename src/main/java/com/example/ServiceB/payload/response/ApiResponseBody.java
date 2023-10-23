package com.example.ServiceB.payload.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ApiResponseBody {

    HttpStatus status;
    String message;
    Object data;
}

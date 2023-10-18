package com.example.ServiceB.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ErrorResponseBody {

    private String messgage;
    private int code;
}

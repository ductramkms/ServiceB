package com.example.ServiceB.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ServiceB.payload.response.ApiResponseBody;

@RestController
public class HomeController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ApiResponseBody home() {
        return ApiResponseBody.builder()
                .message("Home controller")
                .data("MESSAGE: this is home page")
                .build();
    }
}

package com.example.service_b.controller;

import com.example.service_b.payload.response.ApiResponseBody;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

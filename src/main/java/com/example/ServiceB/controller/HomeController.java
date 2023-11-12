package com.example.ServiceB.controller;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ServiceB.payload.response.ApiResponseBody;
import com.example.ServiceB.util.ColorLog;

@RestController
public class HomeController {

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponseBody login(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        try {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            ColorLog.printStackTrace(e);
        }

        return ApiResponseBody.builder().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ApiResponseBody home() {
        return ApiResponseBody.builder()
                .message("Home controller")
                .data("MESSAGE: this is home page")
                .build();
    }
}

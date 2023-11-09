package com.example.ServiceB.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ServiceB.payload.response.ApiResponseBody;

import io.opentelemetry.api.trace.Span;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @GetMapping
    public ApiResponseBody all() {
        Span span = Span.current();
        span.setAttribute("url.query", "?name=Huu Duc");

        return ApiResponseBody.builder().data(new ArrayList<>()).message("List of department")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponseBody getById(@PathVariable("id") Integer id) {
        return ApiResponseBody.builder().data(id).message("Department ID: " + id)
                .build();
    }

}

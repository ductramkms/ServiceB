package com.example.ServiceB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ServiceB.payload.response.ApiResponseBody;
import com.example.ServiceB.service.DepartmentService;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.util.StringUtils;
import io.opentelemetry.api.trace.Span;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    private final MeterRegistry meterRegistry;

    @GetMapping
    public ApiResponseBody all(@RequestParam(required = false) String title) {
        Timer.Sample sample = Timer.start(meterRegistry);

        Counter counter = Counter.builder("get_all_departments")
                .tag("title", StringUtils.isEmpty(title) ? "all" : title)
                .description("A number of requests to /departments endpoint")
                .register(meterRegistry);

        counter.increment();
        sample.stop(Timer.builder("request_time_get_all_departments").register(meterRegistry));

        return ApiResponseBody.builder().data(departmentService.all(title)).message(
                "List of department")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponseBody getById(@PathVariable("id") Integer id) {
        return ApiResponseBody.builder().data(id).message("Department ID: " + id)
                .build();
    }

    @GetMapping("search")
    public ApiResponseBody search(@RequestParam String department, @RequestParam Integer floor) {
        Span span = Span.current();
        span.setAttribute("url.query", "?department=" + department + "&floor=" + floor);

        return ApiResponseBody.builder().build();
    }
}

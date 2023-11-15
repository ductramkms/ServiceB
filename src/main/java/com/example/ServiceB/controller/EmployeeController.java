package com.example.ServiceB.controller;

import com.example.ServiceB.exception.custom.InvalidDataException;
import com.example.ServiceB.exception.custom.ItemAlreadyExistsException;
import com.example.ServiceB.exception.custom.ItemNotFoundException;
import com.example.ServiceB.payload.common.EmployeeBody;
import com.example.ServiceB.payload.response.ApiResponseBody;
import com.example.ServiceB.service.EmployeeService;
import com.example.ServiceB.util.ColorLog;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

import java.util.concurrent.TimeUnit;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/employees")
@Slf4j
public class EmployeeController {

  private final MeterRegistry meterRegistry;

  public EmployeeController(MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  @Autowired
  private EmployeeService employeeService;

  @Timed(value = "controller.employee.get.all")
  @GetMapping
  public ApiResponseBody all() {
    Timer timer = Timer.builder("controller.employee.get.all").register(meterRegistry);

    ApiResponseBody body = timer.record(() -> {
      return ApiResponseBody.builder()
          .status(HttpStatus.OK)
          .message("Get list employees success!")
          .data(employeeService.getAll())
          .build();
    });

    log.info(ColorLog.getLog("GET: /employee & time request = " + timer.max(
        TimeUnit.MILLISECONDS)));

    return body;
  }

  @Timed(value = "controller.employee.get.by.id")
  @GetMapping(value = "/{id}")
  public ApiResponseBody getById(
      @PathVariable Integer id) throws ItemNotFoundException, InvalidDataException {
    Timer timer = Timer.builder("controller.employee.get.by.id").register(meterRegistry);

    ApiResponseBody body = timer.record(() -> {
      try {
        return ApiResponseBody.builder()
            .status(HttpStatus.OK)
            .message("Get employee " + id + " success!")
            .data(employeeService.getById(id))
            .build();
      } catch (ItemNotFoundException e) {
        ColorLog.printStackTrace(e);
      } catch (InvalidDataException e) {
        ColorLog.printStackTrace(e);
      }
      return null;
    });

    log.info(ColorLog.getLog("GET: employee/" + id + " & request time = " + timer.max(
        TimeUnit.MILLISECONDS)));

    return body;
  }

  @Timed(value = "controller.employee.create")
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public ApiResponseBody create(
      @Valid @RequestBody EmployeeBody employeeBody) throws ItemAlreadyExistsException, InvalidDataException {

    Timer timer = Timer.builder("controller.employee.create").register(meterRegistry);

    timer.record(() -> {
      try {
        employeeService.create(employeeBody);
      } catch (ItemAlreadyExistsException e) {
        ColorLog.printStackTrace(e);
      } catch (InvalidDataException e) {
        ColorLog.printStackTrace(e);
      }
    });

    log.info(ColorLog.getLog("POST: employee/" + "  | RequestBody: " + employeeBody.toString()
        + " & request time = " + timer.max(TimeUnit.MILLISECONDS)));

    return ApiResponseBody.builder()
        .status(HttpStatus.CREATED)
        .message("Create employee success!")
        .data(employeeBody)
        .build();
  }

  @Timed(value = "controller.employee.update")
  @PutMapping
  @ResponseStatus(code = HttpStatus.ACCEPTED)
  public ApiResponseBody update(
      @Valid @RequestBody EmployeeBody employeeBody) throws ItemNotFoundException {
    Timer timer = Timer.builder("controller.employee.create").register(meterRegistry);

    timer.record(() -> {
      employeeService.update(employeeBody);
    });

    log.info(ColorLog.getLog("POST: employee/" + "  | RequestBody: " + employeeBody.toString()
        + " & request time = " + timer.max(TimeUnit.MILLISECONDS))
    );

    return ApiResponseBody.builder()
        .status(HttpStatus.ACCEPTED)
        .message("Employee updated")
        .data(employeeBody)
        .build();
  }
}

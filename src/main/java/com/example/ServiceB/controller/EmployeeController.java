package com.example.ServiceB.controller;

import com.example.ServiceB.exception.custom.InvalidDataException;
import com.example.ServiceB.exception.custom.ItemAlreadyExistsException;
import com.example.ServiceB.exception.custom.ItemNotFoundException;
import com.example.ServiceB.payload.common.EmployeeBody;
import com.example.ServiceB.payload.response.ApiResponseBody;
import com.example.ServiceB.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/employee")
@Slf4j
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @GetMapping
  public ApiResponseBody all() {
    log.info("GET: employee/");
    return ApiResponseBody.builder()
        .status(HttpStatus.OK)
        .message("Get list employees success!")
        .data(employeeService.getAll())
        .build();
  }

  @GetMapping(value = "/{id}")
  public ApiResponseBody getById(
      @PathVariable Integer id) throws ItemNotFoundException, InvalidDataException {
    log.info("GET: employee/" + id);

    EmployeeBody resBody = employeeService.getById(id);
    return ApiResponseBody.builder()
        .status(HttpStatus.OK)
        .message("Get employee " + id + " success!")
        .data(resBody)
        .build();
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public ApiResponseBody create(
      @RequestBody EmployeeBody employeeBody)
      throws ItemAlreadyExistsException, InvalidDataException {
    log.info("POST: employee/" + "  | RequestBody: " + employeeBody.toString());
    employeeService.create(employeeBody);
    return ApiResponseBody.builder()
        .status(HttpStatus.CREATED)
        .message("Create employee success!")
        .data(employeeBody)
        .build();
  }
}

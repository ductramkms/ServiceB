package com.example.ServiceB.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ServiceB.exception.custom.ItemAlreadyExistsException;
import com.example.ServiceB.exception.custom.ItemNotFoundException;
import com.example.ServiceB.payload.common.EmployeeBody;
import com.example.ServiceB.service.EmployeeService;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<EmployeeBody> all() {
        return employeeService.getAll();
    }

    // @GetMapping
    // public String all() {
    //     return employeeService.getAll().get(0).getName();
    // }

    @GetMapping(value = "/{id}")
    public EmployeeBody getById(@PathVariable Integer id) throws ItemNotFoundException {
        EmployeeBody resBody = employeeService.getById(id);
        return resBody;
    }

    @PostMapping
    public boolean create(
            @RequestBody EmployeeBody employeeBody) throws ItemAlreadyExistsException {
        boolean resBody = employeeService.create(employeeBody);
        return resBody;
    }
}

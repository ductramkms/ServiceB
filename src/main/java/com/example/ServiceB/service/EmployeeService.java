package com.example.ServiceB.service;

import java.util.List;

import com.example.ServiceB.exception.custom.ItemAlreadyExistsException;
import com.example.ServiceB.exception.custom.ItemNotFoundException;
import com.example.ServiceB.payload.common.EmployeeBody;

public interface EmployeeService {

    List<EmployeeBody> getAll();

    EmployeeBody getById(Integer id) throws ItemNotFoundException;

    boolean create(EmployeeBody body) throws ItemAlreadyExistsException;
}

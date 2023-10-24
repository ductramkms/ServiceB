package com.example.ServiceB.service;

import com.example.ServiceB.exception.custom.ItemAlreadyExistsException;
import com.example.ServiceB.exception.custom.ItemNotFoundException;
import com.example.ServiceB.payload.common.EmployeeBody;
import com.example.ServiceB.payload.response.ListEmployeeBody;

public interface EmployeeService {

  ListEmployeeBody getAll();

  EmployeeBody getById(Integer id) throws ItemNotFoundException;

  void create(EmployeeBody body) throws ItemAlreadyExistsException;
}

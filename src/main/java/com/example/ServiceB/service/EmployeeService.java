package com.example.ServiceB.service;

import com.example.ServiceB.exception.custom.InvalidDataException;
import com.example.ServiceB.exception.custom.ItemAlreadyExistsException;
import com.example.ServiceB.exception.custom.ItemNotFoundException;
import com.example.ServiceB.payload.common.EmployeeBody;
import com.example.ServiceB.payload.response.ListEmployeeBody;

public interface EmployeeService {

  ListEmployeeBody getAll();

  EmployeeBody getById(Integer id) throws ItemNotFoundException, InvalidDataException;

  void create(EmployeeBody body) throws ItemAlreadyExistsException, InvalidDataException;

  void update(EmployeeBody body) throws ItemNotFoundException;

  void delete(Integer id) throws ItemNotFoundException;

  boolean existed(Integer id);
}

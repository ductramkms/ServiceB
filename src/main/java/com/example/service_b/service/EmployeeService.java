package com.example.service_b.service;

import com.example.service_b.exception.custom.InvalidDataException;
import com.example.service_b.exception.custom.ItemAlreadyExistsException;
import com.example.service_b.exception.custom.ItemNotFoundException;
import com.example.service_b.payload.common.EmployeeBody;
import com.example.service_b.payload.response.ListEmployeeBody;

public interface EmployeeService {

  ListEmployeeBody getAll();

  EmployeeBody getById(Integer id) throws ItemNotFoundException, InvalidDataException;

  void create(EmployeeBody body) throws ItemAlreadyExistsException, InvalidDataException;

  void update(EmployeeBody body) throws ItemNotFoundException;

  void delete(Integer id) throws ItemNotFoundException;

  boolean existed(Integer id);
}

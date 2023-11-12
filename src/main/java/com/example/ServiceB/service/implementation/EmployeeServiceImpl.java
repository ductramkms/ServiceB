package com.example.ServiceB.service.implementation;

import com.example.ServiceB.constant.ExceptionMessage;
import com.example.ServiceB.exception.custom.InvalidDataException;
import com.example.ServiceB.exception.custom.ItemAlreadyExistsException;
import com.example.ServiceB.exception.custom.ItemNotFoundException;
import com.example.ServiceB.model.Employee;
import com.example.ServiceB.payload.common.EmployeeBody;
import com.example.ServiceB.payload.response.ListEmployeeBody;
import com.example.ServiceB.repository.EmployeeRepository;
import com.example.ServiceB.service.EmployeeService;
import com.example.ServiceB.util.ColorLog;

import io.micrometer.core.annotation.Timed;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Timed(value = "service.employee.get.all")
  @Override
  public ListEmployeeBody getAll() {
    List<Employee> employees = employeeRepository.findAll();
    List<EmployeeBody> list = employees.stream().map(EmployeeBody::fromEntity).collect(
        Collectors.toList());

    return ListEmployeeBody.builder()
        .total(list.size())
        .employees(list)
        .build();
  }

  @Timed(value = "service.employee.get.by.id")
  @Override
  public EmployeeBody getById(Integer id) throws ItemNotFoundException, InvalidDataException {
    if (id == null || id < 0) {
      String message = ExceptionMessage.EMPLOYEE_ID_CANT_BE_NEGATIVE;
      log.error(ColorLog.getError(message));
      throw new InvalidDataException(message);
    }

    Optional<Employee> emOptional = employeeRepository.findById(id);

    if (!emOptional.isPresent()) {
      String message = String.format(ExceptionMessage.CANT_FIND_EMPLOYEE, id);
      log.error(ColorLog.getError(message));
      throw new ItemNotFoundException(message);
    }
    return EmployeeBody.fromEntity(emOptional.get());
  }

  @Timed(value = "service.employee.create")
  @Override
  public void create(EmployeeBody body) throws ItemAlreadyExistsException, InvalidDataException {
    // validateEmployee(body);

    if (employeeRepository.existsById(body.getEmpId())) {
      String message = String.format(ExceptionMessage.EMPLOYEE_EXISTED, body
          .getEmpId());
      log.error(ColorLog.getError(message));
      throw new ItemAlreadyExistsException(message);
    }

    employeeRepository.save(EmployeeBody.toEntity(body));
  }

  @Timed(value = "service.employee.update")
  @Override
  public void update(EmployeeBody body) throws ItemNotFoundException {
    if (!employeeRepository.existsById(body.getEmpId())) {
      String message = String.format(ExceptionMessage.CANT_FIND_EMPLOYEE, body
          .getEmpId());
      log.error(ColorLog.getError(message));
      throw new ItemNotFoundException(message);
    }

    employeeRepository.save(EmployeeBody.toEntity(body));
  }

  @Timed(value = "service.employee.delete")
  @Override
  public void delete(Integer id) throws ItemNotFoundException {
    employeeRepository.deleteById(id);
  }

  @Timed(value = "service.employee.check.is.existed")
  @Override
  public boolean existed(Integer id) {
    if (id == null) {
      return false;
    }
    return employeeRepository.existsById(id);
  }
}

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

  @Override
  public EmployeeBody getById(Integer id) throws ItemNotFoundException, InvalidDataException {
    if (id == null || id < 0) {
      String message = ExceptionMessage.EMPLOYEE_ID_CANT_BE_NEGATIVE;
      log.error(message);
      throw new InvalidDataException(message);
    }

    Optional<Employee> emOptional = employeeRepository.findById(id);

    if (!emOptional.isPresent()) {
      String message = String.format(ExceptionMessage.CANT_FIND_EMPLOYEE, id);
      log.error(message);
      throw new ItemNotFoundException(message);
    }
    return EmployeeBody.fromEntity(emOptional.get());
  }

  private void validateEmployee(EmployeeBody body) throws InvalidDataException {
    String message = "";
    if (body.getEmpId() < 0) {
      message = String.format(ExceptionMessage.EMPLOYEE_ID_CANT_BE_NEGATIVE,
          body.getEmpId());
    }
    if (body.getSalary() < 0) {
      message = ExceptionMessage.EMPLOYEE_SALARY_CANT_BE_NEGATIVE;
    }
    if (body.getName().length() > 150) {
      message = ExceptionMessage.EMPLOYEE_NAME_TOO_LONG;
    }
    if (body.getDepartment().length() > 150) {
      message = ExceptionMessage.EMPLOYEE_DEPARTMENT_TOO_LONG;
    }

    if (!message.isBlank()) {
      log.error(message);
      throw new InvalidDataException(message);
    }
  }

  @Override
  public void create(EmployeeBody body) throws ItemAlreadyExistsException, InvalidDataException {
    // validateEmployee(body);

    if (employeeRepository.existsById(body.getEmpId())) {
      String message = String.format(ExceptionMessage.EMPLOYEE_EXISTED, body
          .getEmpId());
      log.error(message);
      throw new ItemAlreadyExistsException(message);
    }

    employeeRepository.save(EmployeeBody.toEntity(body));
  }

}

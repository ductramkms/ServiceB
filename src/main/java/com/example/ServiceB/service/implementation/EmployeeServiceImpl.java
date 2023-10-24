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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
      throw new InvalidDataException(String.format(ExceptionMessage.EMPLOYEE_ID_CANT_BE_NEGATIVE,
          id));
    }

    Optional<Employee> emOptional = employeeRepository.findById(id);
    if (!emOptional.isPresent()) {
      throw new ItemNotFoundException(String.format(ExceptionMessage.CANT_FIND_EMPLOYEE, id));
    }
    return EmployeeBody.fromEntity(emOptional.get());
  }

  private void validateEmployee(EmployeeBody body) throws InvalidDataException {
    if (body.getEmpId() < 0) {
      throw new InvalidDataException(String.format(ExceptionMessage.EMPLOYEE_ID_CANT_BE_NEGATIVE,
          body.getEmpId()));
    }
    if (body.getSalary() < 0) {
      throw new InvalidDataException(String.format(
          ExceptionMessage.EMPLOYEE_SALARY_CANT_BE_NEGATIVE, body.getSalary()));
    }
    if (body.getName().length() > 150) {
      throw new InvalidDataException(ExceptionMessage.EMPLOYEE_NAME_TOO_LONG);
    }
    if (body.getDepartment().length() > 150) {
      throw new InvalidDataException(ExceptionMessage.EMPLOYEE_DEPARTMENT_TOO_LONG);
    }
  }

  @Override
  public void create(EmployeeBody body) throws ItemAlreadyExistsException, InvalidDataException {
    validateEmployee(body);

    if (employeeRepository.existsById(body.getEmpId())) {
      throw new ItemAlreadyExistsException(String.format(ExceptionMessage.EMPLOYEE_EXISTED, body
          .getEmpId()));
    }
    employeeRepository.save(EmployeeBody.toEntity(body));
  }

}

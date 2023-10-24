package com.example.ServiceB.service.implementation;

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
  public EmployeeBody getById(Integer id) throws ItemNotFoundException {
    Optional<Employee> emOptional = employeeRepository.findById(id);
    if (!emOptional.isPresent()) {
      throw new ItemNotFoundException("Cannot find the employee with id = " + id);
    }
    return EmployeeBody.fromEntity(emOptional.get());
  }

  @Override
  public void create(EmployeeBody body) throws ItemAlreadyExistsException {
    if (employeeRepository.existsById(body.getEmpId())) {
      throw new ItemAlreadyExistsException("The employ with id = " + body.getEmpId()
          + " has already exited");
    }
    employeeRepository.save(EmployeeBody.toEntity(body));
  }

}

package com.example.ServiceB.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ServiceB.exception.custom.ItemAlreadyExistsException;
import com.example.ServiceB.exception.custom.ItemNotFoundException;
import com.example.ServiceB.model.Employee;
import com.example.ServiceB.payload.common.EmployeeBody;
import com.example.ServiceB.repository.EmployeeRepository;
import com.example.ServiceB.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeBody> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeBody::fromEntity).collect(Collectors.toList());
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
    public boolean create(EmployeeBody body) throws ItemAlreadyExistsException {
        if (employeeRepository.existsById(body.getEmpId())) {
            throw new ItemAlreadyExistsException("The employ with id = " + body.getEmpId() + " has already exited");
        }
        return employeeRepository.save(EmployeeBody.toEntity(body)) != null;
    }

}

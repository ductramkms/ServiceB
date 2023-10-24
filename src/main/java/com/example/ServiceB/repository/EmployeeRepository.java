package com.example.ServiceB.repository;

import com.example.ServiceB.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, Integer> {

}

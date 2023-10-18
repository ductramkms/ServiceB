package com.example.ServiceB.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ServiceB.model.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, Integer> {

}

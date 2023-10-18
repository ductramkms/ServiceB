package com.example.ServiceB.model;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Employee {

    @Id
    private Integer empId;
    private String name;
    private String department;
    private Double salary;
}

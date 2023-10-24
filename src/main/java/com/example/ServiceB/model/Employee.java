package com.example.ServiceB.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class Employee {

  @Id
  private Integer empId;
  private String name;
  private String department;
  private Double salary;
}

package com.example.ServiceB.model;

import lombok.Builder;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("employees")
public class Employee {

  @Id
  private Integer empId;
  private String name;
  private String department;
  private Double salary;
  private String note;
}

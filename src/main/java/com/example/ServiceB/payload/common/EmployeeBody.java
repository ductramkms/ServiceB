package com.example.ServiceB.payload.common;

import com.example.ServiceB.model.Employee;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeBody {

  private Integer empId;
  private String name;
  private String department;
  private Double salary;

  public static EmployeeBody fromEntity(Employee employee) {
    return EmployeeBody.builder()
        .empId(employee.getEmpId())
        .name(employee.getName())
        .department(employee.getDepartment())
        .salary(employee.getSalary())
        .build();
  }

  public static Employee toEntity(EmployeeBody body) {
    return Employee.builder()
        .empId(body.getEmpId())
        .name(body.getName())
        .department(body.getDepartment())
        .salary(body.getSalary())
        .build();
  }
}

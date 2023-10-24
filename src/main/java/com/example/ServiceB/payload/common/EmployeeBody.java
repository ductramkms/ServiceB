package com.example.ServiceB.payload.common;

import com.example.ServiceB.model.Employee;
import java.util.Objects;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmployeeBody body = (EmployeeBody) o;
    return Objects.equals(empId, body.empId) && Objects.equals(name, body.name)
        && Objects.equals(department, body.department) && Objects.equals(salary,
        body.salary);
  }

  @Override
  public int hashCode() {
    return Objects.hash(empId, name, department, salary);
  }
}

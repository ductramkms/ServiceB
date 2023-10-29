package com.example.ServiceB.payload.common;

import com.example.ServiceB.constant.ExceptionMessage;
import com.example.ServiceB.model.Employee;
import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeBody {

  @NotNull(message = "Id cannot be null")
  @Min(value = 0, message = ExceptionMessage.EMPLOYEE_ID_CANT_BE_NEGATIVE)
  private Integer empId;

  @NotBlank(message = "The name cannot be empty")
  @Length(max = 150, message = ExceptionMessage.EMPLOYEE_NAME_TOO_LONG)
  private String name;

  @NotBlank(message = "Department cannot be empty")
  @Length(max = 150, message = ExceptionMessage.EMPLOYEE_DEPARTMENT_TOO_LONG)
  private String department;

  @Min(value = 0, message = ExceptionMessage.EMPLOYEE_SALARY_CANT_BE_NEGATIVE)
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

package com.example.service_b.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.service_b.constant.ExceptionMessage;
import com.example.service_b.exception.custom.InvalidDataException;
import com.example.service_b.exception.custom.ItemAlreadyExistsException;
import com.example.service_b.exception.custom.ItemNotFoundException;
import com.example.service_b.model.Employee;
import com.example.service_b.payload.common.EmployeeBody;
import com.example.service_b.payload.response.ListEmployeeBody;
import com.example.service_b.repository.EmployeeRepository;
import com.example.service_b.service.EmployeeService;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceImplTest {

  @TestConfiguration
  public static class EmployeeServiceTestConfiguration {

    @Bean
    public EmployeeService employeeService() {
      return new EmployeeServiceImpl();
    }
  }

  @Autowired
  EmployeeService employeeService;
  @MockBean
  EmployeeRepository employeeRepository;

  private List<Employee> employeeList;

  @BeforeEach
  public void setup() {
    employeeList = new ArrayList<>();
    employeeList.add(Employee.builder().empId(1).build());
    employeeList.add(Employee.builder().empId(2).build());
    employeeList.add(Employee.builder().empId(3).build());
    employeeList.add(Employee.builder().empId(4).build());
    employeeList.add(Employee.builder().empId(5).build());
  }

  @Test
  void create_ValidData_CreateSuccess() {
    EmployeeBody req = EmployeeBody.builder().empId(1).salary(1.0).name("Huu Duc")
        .department("Software").build();

    Assertions.assertDoesNotThrow(() -> {
      employeeService.create(req);
    });
  }

  @Test
  void create_ExistedId_ThrowException() {
    EmployeeBody req = EmployeeBody.builder().empId(1).salary(1.0).name("Huu Duc")
        .department("Software").build();

    Mockito.when(employeeRepository.existsById(1)).thenReturn(true);

    Exception exception = Assertions.assertThrows(ItemAlreadyExistsException.class, () -> {
      employeeService.create(req);
    });

    Assertions.assertEquals(exception.getMessage(),
        String.format(ExceptionMessage.EMPLOYEE_EXISTED, 1));
  }

  @Test
  void getAll_ListEmployee() {
    Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
    ListEmployeeBody response = employeeService.getAll();
    Assertions.assertEquals(response.getTotal(), 5);
  }

  @Test
  void getById_ExistedId_EmployeeData() throws InvalidDataException {
    Employee employee = Employee.builder().empId(1).name("Huu Duc").salary(1.0)
        .department("Software").build();
    Mockito.when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

    EmployeeBody body = employeeService.getById(1);
    Assertions.assertEquals(body, EmployeeBody.fromEntity(employee));
  }

  @Test
  void getById_UnExistedId_ThrowItemNotFoundException() {
    Mockito.when(employeeRepository.findById(2)).thenReturn(Optional.empty());

    Exception exception = Assertions.assertThrows(ItemNotFoundException.class, () -> {
      EmployeeBody body = employeeService.getById(2);
    });

    Assertions.assertEquals(exception.getMessage(),
        String.format(ExceptionMessage.CANT_FIND_EMPLOYEE, 2));
  }
}

package com.example.service_b.controller;

import com.example.service_b.exception.custom.InvalidDataException;
import com.example.service_b.model.Employee;
import com.example.service_b.payload.common.EmployeeBody;
import com.example.service_b.payload.response.ListEmployeeBody;
import com.example.service_b.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

        @Autowired
        MockMvc mockMvc;

        @MockBean
        EmployeeService employeeService;

        private ListEmployeeBody listEmployeeBody;

        @BeforeEach
        void setUp() {
                List<Employee> employeeList;
                employeeList = new ArrayList<>();
                employeeList.add(Employee.builder().empId(1).build());
                employeeList.add(Employee.builder().empId(2).build());
                employeeList.add(Employee.builder().empId(3).build());
                employeeList.add(Employee.builder().empId(4).build());
                employeeList.add(Employee.builder().empId(5).build());
                listEmployeeBody = ListEmployeeBody.builder()
                                .total(5)
                                .employees(employeeList.stream().map(EmployeeBody::fromEntity)
                                                .collect(Collectors
                                                                .toList()))
                                .build();
        }

        @Test
        void getAllEmployees_OK_IfGetEmployeesSuccess() throws Exception {
                Mockito.when(employeeService.getAll()).thenReturn(listEmployeeBody);
                mockMvc.perform(MockMvcRequestBuilders.get("/employee").contentType(
                                MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(
                                                MockMvcResultMatchers.content()
                                                                .contentTypeCompatibleWith(
                                                                                MediaType.APPLICATION_JSON));
        }

        @Test
        void getById_Employee_IfSuccess() throws Exception {
                Mockito.when(employeeService.getById(1)).thenReturn(EmployeeBody.builder().empId(1)
                                .build());

                mockMvc.perform(
                                MockMvcRequestBuilders.get("/employee/1").contentType(
                                                MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(
                                                MockMvcResultMatchers.content()
                                                                .contentTypeCompatibleWith(
                                                                                MediaType.APPLICATION_JSON));
        }

        @Test
        void create_ValidData_Created() throws Exception {
                EmployeeBody body = EmployeeBody.builder().empId(1).name("Huu Duc").salary(1.0)
                                .department("Software").build();

                Mockito.doNothing().when(employeeService).create(body);

                ObjectMapper objectMapper = new ObjectMapper();
                mockMvc.perform(MockMvcRequestBuilders.post("/employee")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(body))
                )
                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                .andExpect(
                                                MockMvcResultMatchers.content()
                                                                .contentTypeCompatibleWith(
                                                                                MediaType.APPLICATION_JSON));
        }

        @Test
        void create_InvalidData_BadRequest() throws Exception {
                EmployeeBody body = EmployeeBody.builder().empId(-1).name("Huu Duc").salary(1.0)
                                .department("Software").build();

                Mockito.doThrow(new InvalidDataException("Exception invalid data")).when(
                                employeeService)
                                .create(body);

                ObjectMapper objectMapper = new ObjectMapper();
                mockMvc.perform(MockMvcRequestBuilders.post("/employee")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(body))
                )
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(
                                                MockMvcResultMatchers.content()
                                                                .contentTypeCompatibleWith(
                                                                                MediaType.APPLICATION_JSON));
        }
}
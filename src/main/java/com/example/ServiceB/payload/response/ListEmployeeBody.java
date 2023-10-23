package com.example.ServiceB.payload.response;

import java.util.List;

import com.example.ServiceB.payload.common.EmployeeBody;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListEmployeeBody {

    private Integer total;
    private List<EmployeeBody> employees;
}

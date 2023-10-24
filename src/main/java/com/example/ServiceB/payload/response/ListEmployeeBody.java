package com.example.ServiceB.payload.response;

import com.example.ServiceB.payload.common.EmployeeBody;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListEmployeeBody {

  private Integer total;
  private List<EmployeeBody> employees;
}

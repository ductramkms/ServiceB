package com.example.ServiceB.constant;

public class ExceptionMessage {

  public static final String CANT_FIND_EMPLOYEE = "Can't find the employee with id = %d";
  public static final String EMPLOYEE_EXISTED = "The employee with id = %d has already existed";

  public static final String EMPLOYEE_ID_CANT_BE_NEGATIVE = "The id of employee cannot be negative, but we got id = %d";
  public static final String EMPLOYEE_SALARY_CANT_BE_NEGATIVE = "The salary of employee cannot be negative, but we got salary = %f";
  public static final String EMPLOYEE_NAME_TOO_LONG = "The length of employee name too long, it must be less than 150 characters";
  public static final String EMPLOYEE_DEPARTMENT_TOO_LONG = "The length of department too long, it must be less than 150 characters";
}

package com.bootcamp.demo.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
	private int emp_id;
	private String emp_name;
	private String emp_city;
	private String emp_phone;
	private double java_exp;
	private double spring_exp;

}

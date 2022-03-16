package com.bootcamp.demo.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse extends Employee {
	private String status;

	public EmployeeResponse(Employee employee) {
		super(employee.getEmp_id(), employee.getEmp_name(), employee.getEmp_city(), employee.getEmp_phone(),
				employee.getJava_exp(), employee.getSpring_exp());
	}
	
	public EmployeeResponse(Employee employee, String status) {
		super(employee.getEmp_id(), employee.getEmp_name(), employee.getEmp_city(), employee.getEmp_phone(),
				employee.getJava_exp(), employee.getSpring_exp());
		this.status = status;
	}

}

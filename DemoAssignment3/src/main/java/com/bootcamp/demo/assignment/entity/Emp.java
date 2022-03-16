package com.bootcamp.demo.assignment.entity;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("emp")
public class Emp {

	@PrimaryKey
	private int emp_id;
	@Column
	private String emp_name;
	@Column
	private String emp_city;
	@Column
	private String emp_phone;

}
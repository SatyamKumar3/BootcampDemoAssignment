package com.bootcamp.demo.assignment.entity;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("emp_skill")
public class EmpSkill {

	@PrimaryKeyColumn(name = "emp_id", type = PrimaryKeyType.PARTITIONED)
	private String empId;
	@PrimaryKeyColumn(name = "java_exp", ordinal = 0)
	private double javaExperience;
	@PrimaryKeyColumn(name = "spring_exp", ordinal = 1)
	private double springExperience;

}

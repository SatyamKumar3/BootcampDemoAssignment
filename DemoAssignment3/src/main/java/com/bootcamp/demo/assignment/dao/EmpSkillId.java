package com.bootcamp.demo.assignment.dao;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@PrimaryKeyClass
public class EmpSkillId {
	@PrimaryKeyColumn( type = PrimaryKeyType.PARTITIONED)
	private String emp_id ;
	@PrimaryKeyColumn
	private double java_exp ;
	@PrimaryKeyColumn
	private double spring_exp ;
	
}

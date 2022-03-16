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
@Table("job")
public class Job {

	@PrimaryKey
	private String job_id;
	@Column
	private String job_name;
	@Column
	private Double java_exp;
	@Column
	private Double spring_exp;

}
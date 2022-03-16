package com.bootcamp.demo.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {
	private Integer job_id;
	private String job_name;
	private Double java_exp;
	private Double spring_exp;

}

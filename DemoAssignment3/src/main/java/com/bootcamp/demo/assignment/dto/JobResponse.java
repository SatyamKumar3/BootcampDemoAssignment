package com.bootcamp.demo.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobResponse extends Job {
	private String status;

	public JobResponse(Job job) {
		super(job.getJob_id(), job.getJob_name(), job.getJava_exp(), job.getSpring_exp());
	}

	public JobResponse(Job job, String status) {
		super(job.getJob_id(), job.getJob_name(), job.getJava_exp(), job.getSpring_exp());
		this.status = status;
	}

}

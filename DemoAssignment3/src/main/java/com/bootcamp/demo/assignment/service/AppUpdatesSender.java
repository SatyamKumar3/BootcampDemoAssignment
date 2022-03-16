package com.bootcamp.demo.assignment.service;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import com.bootcamp.demo.assignment.ApplicationConstants;
import com.bootcamp.demo.assignment.dto.Employee;

import lombok.Setter;

public class AppUpdatesSender implements Callable<ListenableFuture<SendResult<String, Employee>>> {

	@Autowired(required = false)
	@Setter
	private Employee employee;
	
	@Autowired
	private KafkaTemplate<String, Employee> kafkaTemplate;
	
	@Override
	public ListenableFuture<SendResult<String, Employee>> call() throws Exception {
		ListenableFuture<SendResult<String, Employee>> listenableFuture = kafkaTemplate.send(ApplicationConstants.APP_UPDATE_TOPIC_NAME, String.valueOf(employee.getEmp_id()), employee);
		return listenableFuture;
	}

	public AppUpdatesSender(Employee employee) {
		this.employee = employee;
	}
}

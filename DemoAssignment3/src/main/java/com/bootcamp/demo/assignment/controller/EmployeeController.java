package com.bootcamp.demo.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.demo.assignment.dto.Employee;
import com.bootcamp.demo.assignment.dto.EmployeeResponse;
import com.bootcamp.demo.assignment.dto.Job;
import com.bootcamp.demo.assignment.dto.JobResponse;
import com.bootcamp.demo.assignment.exception.DuplicateRecordException;
import com.bootcamp.demo.assignment.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/createEmployee")
	public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody Employee employee) {
		return employeeService.createEmployee(employee);
	}

	@GetMapping("/findEmpSkillset")
	public ResponseEntity<List<Employee>> findEmpSkillset(@RequestBody Employee employee) {
		return employeeService.findEmpSkillset(employee);
	}

	@PostMapping("/createJobProfile")
	public ResponseEntity<JobResponse> createEmployee(@RequestBody Job job) {
		ResponseEntity<JobResponse> responseEntity = null;
		JobResponse jobResponse = new JobResponse(job);
		try {
			jobResponse = employeeService.createJobProfile(job);
			responseEntity = new ResponseEntity<>(jobResponse, HttpStatus.CREATED);
		} catch (DuplicateRecordException e) {
			jobResponse.setStatus("Already Exists");
			responseEntity = new ResponseEntity<>(jobResponse, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return responseEntity;
	}

	@GetMapping("/findEmpForJobID")
	public ResponseEntity<List<Employee>> findEmpForJobID(@RequestBody Job job) {
		return employeeService.findEmpForJobID(job);
	}

//	@GetMapping("/getJobProfileFromCache")
//	public ResponseEntity<Job> getJobProfileFromCache(@RequestBody Job job) {
//		return employeeService.getJobProfileFromCache(job);
//	}

	@GetMapping("/getJobProfileFromCache")
	public ResponseEntity<Job> getJobProfileFromCache(@RequestBody Job jobQueryObj) {
		ResponseEntity<Job> responseEntity = null;
		try {
			Job job = employeeService.getJobProfileFromCache(jobQueryObj);
			if (job != null) {
				responseEntity = new ResponseEntity<Job>(job, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<Job>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<Job>(HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return responseEntity;

	}
}

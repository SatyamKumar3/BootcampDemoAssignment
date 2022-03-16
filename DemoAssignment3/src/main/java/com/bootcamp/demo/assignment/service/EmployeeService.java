package com.bootcamp.demo.assignment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.bootcamp.demo.assignment.ApplicationConstants;
import com.bootcamp.demo.assignment.dao.EmpDao;
import com.bootcamp.demo.assignment.dao.EmpSkillDao;
import com.bootcamp.demo.assignment.dao.JobDao;
import com.bootcamp.demo.assignment.dto.Employee;
import com.bootcamp.demo.assignment.dto.EmployeeResponse;
import com.bootcamp.demo.assignment.dto.Job;
import com.bootcamp.demo.assignment.dto.JobResponse;
import com.bootcamp.demo.assignment.entity.Emp;
import com.bootcamp.demo.assignment.entity.EmpSkill;
import com.bootcamp.demo.assignment.exception.DuplicateRecordException;
import com.bootcamp.demo.assignment.util.ApplicationUtil;

import lombok.Getter;

@Service
public class EmployeeService {
	@Autowired
	private EmpDao empDao;

	@Autowired
	private EmpSkillDao empSkillDao;

	@Autowired
	private JobDao jobDao;

	@Autowired
	private KafkaTemplate<String, Employee> kafkaTemplate;

	@Getter
	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);
	
	public ResponseEntity<EmployeeResponse> createEmployee(Employee employee) {
		ResponseEntity<EmployeeResponse> responseEntity = null;
		EmployeeResponse employeeResponse = new EmployeeResponse(employee);
		if (!empDao.existsById(employee.getEmp_id())) {
			Emp empEntity = empDao.save(ApplicationUtil.dtoToEmpEntity(employee));
			EmpSkill empSkill = empSkillDao.save(ApplicationUtil.dtoToEmpSkillEntity(employee));

			employeeResponse.setEmp_id(empEntity.getEmp_id());
			employeeResponse.setEmp_name(empEntity.getEmp_name());
			employeeResponse.setEmp_city(empEntity.getEmp_city());
			employeeResponse.setEmp_phone(empEntity.getEmp_phone());
			employeeResponse.setJava_exp(empSkill.getJavaExperience());
			employeeResponse.setSpring_exp(empSkill.getSpringExperience());
			employeeResponse.setStatus("Created");

			LOGGER.warn("Submitting AppUpdates Sender Job...");
//			executorService.submit(new AppUpdatesSender(employee));
			executorService.submit(() -> {
				kafkaTemplate.send(ApplicationConstants.APP_UPDATE_TOPIC_NAME, String.valueOf(employee.getEmp_id()), employee);
			});

			responseEntity = new ResponseEntity<>(employeeResponse, HttpStatus.CREATED);
		} else {
			employeeResponse.setStatus("Already Exists");
			responseEntity = new ResponseEntity<>(employeeResponse, HttpStatus.OK);
		}
		return responseEntity;
	}

//	public ResponseEntity<List<Employee>> findEmpSkillset(Employee employee) {
//		ResponseEntity<List<Employee>> responseEntity = null;
//		List<EmpSkill> empSkills = null;
//		List<Employee> fetchedEmployees = new ArrayList<Employee>();
//
//		try {
//			double java_exp = employee.getJava_exp();
//			empSkills = empSkillRepository.findByjavaExperienceGreaterThanEqual(java_exp);
//			if (empSkills != null && !empSkills.isEmpty()) {
//				Employee fetchedEmployee = new Employee();
//				empSkills.stream().forEach(empSkill -> {
//					Integer empId = Integer.parseInt(empSkill.getEmpId());
//					fetchedEmployee.setEmp_id(empId);
//					fetchedEmployee.setJava_exp(empSkill.getJavaExperience());
//					fetchedEmployee.setSpring_exp(empSkill.getSpringExperience());
//
//					Optional<Emp> optEmp = empRepository.findById(empId);
//					optEmp.ifPresent(emp -> {
//						fetchedEmployee.setEmp_name(emp.getEmp_name());
//						fetchedEmployee.setEmp_city(emp.getEmp_city());
//						fetchedEmployee.setEmp_phone(emp.getEmp_phone());
//						fetchedEmployees.add(fetchedEmployee);
//					});
//				});
//
//				responseEntity = new ResponseEntity<List<Employee>>(fetchedEmployees, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			responseEntity = new ResponseEntity<List<Employee>>(HttpStatus.INTERNAL_SERVER_ERROR);
//			e.printStackTrace();
//
//		}
//		return responseEntity;
//	}

	public ResponseEntity<List<Employee>> findEmpSkillset(Employee employee) {
		ResponseEntity<List<Employee>> responseEntity = null;
		List<EmpSkill> empSkills = null;
		List<Employee> fetchedEmployees = new ArrayList<Employee>();

		try {
			double java_exp = employee.getJava_exp();
			double spring_exp = employee.getSpring_exp();
			// it's very inefficient, should not be used.. @TODO find an alternative way
			empSkills = empSkillDao.findAll();

			if (empSkills != null && !empSkills.isEmpty()) {
				empSkills.stream().filter(empSkill -> empSkill.getJavaExperience() >= java_exp)
						.filter(empSkill -> empSkill.getSpringExperience() >= spring_exp).forEach(empSkill -> {
							Integer empId = Integer.parseInt(empSkill.getEmpId());
							Employee fetchedEmployee = new Employee();
							fetchedEmployee.setEmp_id(empId);
							fetchedEmployee.setJava_exp(empSkill.getJavaExperience());
							fetchedEmployee.setSpring_exp(empSkill.getSpringExperience());

							Optional<Emp> optEmp = empDao.findById(empId);
							optEmp.ifPresent(emp -> {
								fetchedEmployee.setEmp_name(emp.getEmp_name());
								fetchedEmployee.setEmp_city(emp.getEmp_city());
								fetchedEmployee.setEmp_phone(emp.getEmp_phone());
							});

							fetchedEmployees.add(fetchedEmployee);
						});
			}
			responseEntity = new ResponseEntity<List<Employee>>(fetchedEmployees, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<List<Employee>>(HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();

		}
		return responseEntity;
	}

	public JobResponse createJobProfile(Job job) {
		JobResponse jobResponse = null;
		if (!jobDao.existsById(String.valueOf(job.getJob_id()))) {
			com.bootcamp.demo.assignment.entity.Job jobEntity = jobDao.save(ApplicationUtil.dtoToJobEntity(job));

			jobResponse = new JobResponse();
			jobResponse.setJob_id(Integer.valueOf(jobEntity.getJob_id()));
			jobResponse.setJob_name(jobEntity.getJob_name());
			jobResponse.setJava_exp(jobEntity.getJava_exp());
			jobResponse.setSpring_exp(jobEntity.getSpring_exp());
			jobResponse.setStatus("Created");

		} else {
			throw new DuplicateRecordException("Job profile with id " + job.getJob_id() + " already exists");
		}

		return jobResponse;
	}

	public ResponseEntity<List<Employee>> findEmpForJobID(Job job) {
		ResponseEntity<List<Employee>> responseEntity = null;
		List<Employee> fetchedEmployees = new ArrayList<Employee>();

		try {
			Optional<com.bootcamp.demo.assignment.entity.Job> optJobEntity = jobDao
					.findById(String.valueOf(job.getJob_id()));
			if (optJobEntity.isPresent()) {
				com.bootcamp.demo.assignment.entity.Job jobEntity = optJobEntity.get();

				Employee employee = new Employee();
				employee.setJava_exp(jobEntity.getJava_exp());
				employee.setSpring_exp(jobEntity.getSpring_exp());

				ResponseEntity<List<Employee>> findEmpSkillset = findEmpSkillset(employee);
				if (findEmpSkillset != null) {
					fetchedEmployees = findEmpSkillset.getBody();
				}
			}

			responseEntity = new ResponseEntity<List<Employee>>(fetchedEmployees, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<List<Employee>>(HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();

		}
		return responseEntity;
	}

//	public ResponseEntity<Job> getJobProfileFromCache(Job job) {
//		ResponseEntity<Job> responseEntity = null;
//		// get from Hazelcast first
//
//		Optional<com.bootcamp.demo.assignment.entity.Job> optJobEntity = jobDao
//				.findById(String.valueOf(job.getJob_id()));
//		if (optJobEntity.isPresent()) {
//			// load into Hazelcast cache
//			com.bootcamp.demo.assignment.entity.Job jobEntity = optJobEntity.get();
//			Job fetchedJob = new Job();
//			fetchedJob.setJob_id(Integer.parseInt(jobEntity.getJob_id()));
//			fetchedJob.setJob_name(jobEntity.getJob_name());
//			fetchedJob.setJava_exp(jobEntity.getJava_exp());
//			fetchedJob.setSpring_exp(jobEntity.getSpring_exp());
//			responseEntity = new ResponseEntity<Job>(fetchedJob, HttpStatus.OK);
//		} else {
//			responseEntity = new ResponseEntity<Job>(HttpStatus.NOT_FOUND);
//		}
//
//		return responseEntity;
//	}

	@Cacheable("job")
	public Job getJobProfileFromCache(Job jobQueryObj) {
		Job job = null;
		LOGGER.warn("Fetching Job profile from database for Job ID: " + jobQueryObj.getJob_id() + "...");
		Optional<com.bootcamp.demo.assignment.entity.Job> optJobEntity = jobDao
				.findById(String.valueOf(jobQueryObj.getJob_id()));
		if (optJobEntity.isPresent()) {
			com.bootcamp.demo.assignment.entity.Job jobEntity = optJobEntity.get();
			job = new Job();
			job.setJob_id(Integer.parseInt(jobEntity.getJob_id()));
			job.setJob_name(jobEntity.getJob_name());
			job.setJava_exp(jobEntity.getJava_exp());
			job.setSpring_exp(jobEntity.getSpring_exp());
		}

		return job;
	}
}

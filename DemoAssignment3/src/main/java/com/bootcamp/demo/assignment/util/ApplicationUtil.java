package com.bootcamp.demo.assignment.util;

import com.bootcamp.demo.assignment.dto.Employee;
import com.bootcamp.demo.assignment.dto.Job;
import com.bootcamp.demo.assignment.entity.Emp;
import com.bootcamp.demo.assignment.entity.EmpSkill;

public class ApplicationUtil {

	public static Emp dtoToEmpEntity(Employee emp) {
		Emp empEntity = new Emp();
		empEntity.setEmp_id(emp.getEmp_id());
		empEntity.setEmp_name(emp.getEmp_name());
		empEntity.setEmp_city(emp.getEmp_city());
		empEntity.setEmp_phone(emp.getEmp_phone());
		return empEntity;
	}

	public static EmpSkill dtoToEmpSkillEntity(Employee employee) {
		EmpSkill empSkill = new EmpSkill();
		empSkill.setEmpId(String.valueOf(employee.getEmp_id()));
		empSkill.setJavaExperience(employee.getJava_exp());
		empSkill.setSpringExperience(employee.getSpring_exp());
		return empSkill;
	}

	public static com.bootcamp.demo.assignment.entity.Job dtoToJobEntity(Job job) {
		com.bootcamp.demo.assignment.entity.Job jobEntity = new com.bootcamp.demo.assignment.entity.Job();
		jobEntity.setJob_id(String.valueOf(job.getJob_id()));
		jobEntity.setJob_name(job.getJob_name());
		jobEntity.setJava_exp(job.getJava_exp());
		jobEntity.setSpring_exp(job.getSpring_exp());
		return jobEntity;

	}
}

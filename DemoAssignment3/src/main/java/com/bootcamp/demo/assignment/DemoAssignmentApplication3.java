package com.bootcamp.demo.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DemoAssignmentApplication3 {

	public static void main(String[] args) {
		SpringApplication.run(DemoAssignmentApplication3.class, args);
	}

}

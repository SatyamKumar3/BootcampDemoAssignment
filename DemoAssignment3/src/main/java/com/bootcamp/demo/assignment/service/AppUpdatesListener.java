package com.bootcamp.demo.assignment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.bootcamp.demo.assignment.ApplicationConstants;
import com.bootcamp.demo.assignment.dto.Employee;

@Service
public class AppUpdatesListener {

	@Autowired
	private KafkaTemplate<String, Employee> kafkaTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(AppUpdatesListener.class);

	@KafkaListener(topics = ApplicationConstants.APP_UPDATE_TOPIC_NAME, groupId = ApplicationConstants.BY_CONSUMER_GROUP_ID)
//	@KafkaListener(topics = ApplicationConstants.APP_UPDATE_TOPIC_NAME)
	public void listenAppUpdates(@Payload Employee employee, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
		LOGGER.warn("Received Message in " + ApplicationConstants.APP_UPDATE_TOPIC_NAME + ": " + key + "= " + employee);
		if (validateEmployee(employee)) {
			kafkaTemplate.send(ApplicationConstants.EMP_UPDATE_TOPIC_NAME, String.valueOf(employee.getEmp_id()),
					employee);
		} else {
			kafkaTemplate.send(ApplicationConstants.EMP_UPDATE_DLQ_TOPIC_NAME, String.valueOf(employee.getEmp_id()),
					employee);
		}
	}

	public boolean validateEmployee(Employee employee) {
		boolean isValid = true;
		if (employee == null) {
			isValid = false;
		} else if (employee.getEmp_id() <= 0) {
			isValid = false;
		} else if (employee.getEmp_name() == null || employee.getEmp_name().isEmpty()) {
			isValid = false;
		} else if (employee.getEmp_phone() == null || employee.getEmp_phone().isEmpty()) {
			isValid = false;
		} else if (employee.getJava_exp() < 0) {
			isValid = false;
		} else if (employee.getSpring_exp() < 0) {
			isValid = false;
		}

		return isValid;
	}

}

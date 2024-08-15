package Microservice.dashboard_service.consumer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import Microservice.dashboard_service.dto.EnrollmentStatusDto;
import Microservice.dashboard_service.entity.EnrollmentStatus;

@Service
public class EnrollmentStatusConsumer {

	private final Logger LOGGER = LoggerFactory.getLogger(EnrollmentStatusConsumer.class);
	List<EnrollmentStatus> enrollmentStatusList = new ArrayList<>();

	@KafkaListener(topics = "enrollment-status", groupId = "enrollments", containerFactory = "kafkaListenerContainerFactoryEnrollment")
	public void consume(@Payload EnrollmentStatusDto enrollmentStatus) {
		LOGGER.info("Consumed Enrollment: ");
		EnrollmentStatus enrollmentEntity=EnrollmentStatus.builder().timeStamp(enrollmentStatus.getTimeStamp())
				.status(enrollmentStatus.getStatus()).build();
		enrollmentStatusList.add(enrollmentEntity);
		LOGGER.info("Consumed Enrollment Status: {}",enrollmentStatus);
	}
	
	public List<EnrollmentStatus> getEnrollmentStatusList() {
		return enrollmentStatusList;
	}
}

package Microservice.dashboard_service.consumer;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import Microservice.dashboard_service.dto.EmailStatusDto;
import Microservice.dashboard_service.entity.EmailStatus;

@Service
public class EmailConsumer {

	private final Logger LOGGER = LoggerFactory.getLogger(EmailConsumer.class);
	List<EmailStatus> emailStatusList = new ArrayList<>();

	@KafkaListener(topics = "email-status", groupId = "emails", containerFactory = "kafkaListenerContainerFactoryEmail")
	public void consume(EmailStatusDto emailStatus) {
		LOGGER.info("Consumed Email: ");
		LocalDateTime localDateTime = LocalDateTime.parse(emailStatus.getTimeStamp());
		Timestamp timestamp = Timestamp.valueOf(localDateTime);
		EmailStatus emailEntity = EmailStatus.builder().timeStamp(timestamp)
				.status(emailStatus.getStatus()).build();
		emailStatusList.add(emailEntity);
		LOGGER.info("Consumed Email Status;{}", emailStatus);
	}

	public List<EmailStatus> getEmailStatusList() {
		return emailStatusList;
	}
}

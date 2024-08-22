package Microservice.dashboard_service.consumer;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import Microservice.dashboard_service.dto.EmailStatusDto;
import Microservice.dashboard_service.entity.EmailStatus;

@Service
public class EmailConsumer {

	@Autowired
	private RedisTemplate<String, EmailStatus> redisTemplate;

	private static final String REDIS_LIST_KEY = "emailStatusList";
	private final Logger LOGGER = LoggerFactory.getLogger(EmailConsumer.class);

	@KafkaListener(topics = "email-status", groupId = "emails", containerFactory = "kafkaListenerContainerFactoryEmail")
	public void consume(EmailStatusDto emailStatus) {
		LOGGER.info("Consumed Email: ");
		LocalDateTime localDateTime = LocalDateTime.parse(emailStatus.getTimeStamp());
		Timestamp timestamp = Timestamp.valueOf(localDateTime);
		EmailStatus emailEntity = EmailStatus.builder().timeStamp(timestamp).status(emailStatus.getStatus()).build();
		redisTemplate.opsForList().rightPush(REDIS_LIST_KEY, emailEntity);
		LOGGER.info("Consumed Email Status: {}", emailStatus);
	}

	public List<EmailStatus> getEmailStatusList() {
		return redisTemplate.opsForList().range(REDIS_LIST_KEY, 0, -1);
	}
}

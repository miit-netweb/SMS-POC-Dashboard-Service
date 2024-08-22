package Microservice.dashboard_service.consumer;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import Microservice.dashboard_service.dto.EnrollmentStatusDto;
import Microservice.dashboard_service.entity.EnrollmentStatus;

@Service
public class EnrollmentStatusConsumer {

	@Autowired
	private RedisTemplate<String, EnrollmentStatus> redisTemplate;

	private static final String REDIS_LIST_KEY = "enrollStatusList";
	private final Logger LOGGER = LoggerFactory.getLogger(EnrollmentStatusConsumer.class);

	@KafkaListener(topics = "enrollment-status", groupId = "enrollments", containerFactory = "kafkaListenerContainerFactoryEnrollment")
	public void consume(@Payload EnrollmentStatusDto enrollmentStatus) {
		LOGGER.info("Consumed Enrollment: ");
		LocalDateTime localDateTime = LocalDateTime.parse(enrollmentStatus.getTimeStamp());
		Timestamp timestamp = Timestamp.valueOf(localDateTime);
		EnrollmentStatus enrollmentEntity = EnrollmentStatus.builder().timeStamp(timestamp)
				.status(enrollmentStatus.getStatus()).build();

		redisTemplate.opsForList().rightPush(REDIS_LIST_KEY, enrollmentEntity);
		LOGGER.info("Consumed Enrollment Status: {}", enrollmentStatus);
	}

	public List<EnrollmentStatus> getEnrollmentStatusList() {
		return redisTemplate.opsForList().range(REDIS_LIST_KEY, 0, -1);
	}
}

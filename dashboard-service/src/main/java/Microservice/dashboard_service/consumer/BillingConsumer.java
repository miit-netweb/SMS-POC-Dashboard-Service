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

import Microservice.dashboard_service.dto.BillingStatusDto;
import Microservice.dashboard_service.entity.BillingStatus;

@Service
public class BillingConsumer {

	@Autowired
	private RedisTemplate<String, BillingStatus> redisTemplate;

	private static final String REDIS_LIST_KEY = "billingStatusList";
	private final Logger LOGGER = LoggerFactory.getLogger(BillingConsumer.class);

	@KafkaListener(topics = "billing-status", groupId = "billings", containerFactory = "kafkaListenerContainerFactoryBilling")
	public void consume(BillingStatusDto billingStatus) {
		LOGGER.info("Consumed Billing: ");
		LocalDateTime localDateTime = LocalDateTime.parse(billingStatus.getTimeStamp());
		Timestamp timestamp = Timestamp.valueOf(localDateTime);
		BillingStatus billingEntity = BillingStatus.builder().timeStamp(timestamp).status(billingStatus.getStatus())
				.build();

		redisTemplate.opsForList().rightPush(REDIS_LIST_KEY, billingEntity);
		LOGGER.info("Consumed Email Status: {}", billingStatus);
	}

	public List<BillingStatus> getBillingStatusList() {
		return redisTemplate.opsForList().range(REDIS_LIST_KEY, 0, -1);
	}
}

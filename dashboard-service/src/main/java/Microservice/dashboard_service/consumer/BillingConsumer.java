package Microservice.dashboard_service.consumer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import Microservice.dashboard_service.dto.BillingStatusDto;
import Microservice.dashboard_service.entity.BillingStatus;

@Service
public class BillingConsumer {

	private final Logger LOGGER = LoggerFactory.getLogger(BillingConsumer.class);
	List<BillingStatus> billingStatusList = new ArrayList<>();

	@KafkaListener(topics = "billing-status", groupId = "billings", containerFactory = "kafkaListenerContainerFactoryBilling")
	public void consume(BillingStatusDto billingStatus) {
		LOGGER.info("Consumed Billing: ");
		BillingStatus billingEntity = BillingStatus.builder().timeStamp(billingStatus.getTimeStamp())
				.status(billingStatus.getStatus()).build();
		billingStatusList.add(billingEntity);
		LOGGER.info("Comsumed Billing Status:{}", billingStatus);
	}

	public List<BillingStatus> getBillingStatusList() {
		return billingStatusList;
	}
}

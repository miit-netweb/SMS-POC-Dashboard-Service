package Microservice.dashboard_service.consumer;

import Microservice.dashboard_service.dto.BillingStatus;
import Microservice.dashboard_service.dto.EmailStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BillingConsumer {

    
   private String subscriptionBillingDtoStr;

    private final Logger LOGGER = LoggerFactory.getLogger(BillingConsumer.class);

    @KafkaListener(topics = "billing-status", groupId = "billings",containerFactory = "kafkaListenerContainerFactoryBilling")
    public void consume(BillingStatus billingStatus) {
        System.out.println("Consumed Billing: ");
        System.out.println(billingStatus.toString());
    }
}

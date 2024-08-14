package Microservice.dashboard_service.consumer;

import Microservice.dashboard_service.dto.EmailStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {

    
   private String subscriptionBillingDtoStr;

    private final Logger LOGGER = LoggerFactory.getLogger(EmailConsumer.class);

    @KafkaListener(topics = "email-status", groupId = "emails",containerFactory = "kafkaListenerContainerFactoryEmail")
    public void consume(EmailStatus emailStatus) {
        System.out.println("Consumed Email: ");
        System.out.println(emailStatus.toString());
    }
}

package Microservice.dashboard_service.consumer;

import Microservice.dashboard_service.dto.EmailStatus;
import Microservice.dashboard_service.dto.EnrollmentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentStatusConsumer {

    
   private String subscriptionBillingDtoStr;

    private final Logger LOGGER = LoggerFactory.getLogger(EnrollmentStatusConsumer.class);

    @KafkaListener(topics = "enrollment-status", groupId = "enrollments",containerFactory = "kafkaListenerContainerFactoryEnrollment")
    public void consume(@Payload EnrollmentStatus enrollmentStatus) {
        System.out.println("Consumed Enrollment: ");
        System.out.println(enrollmentStatus.toString());
    }
}

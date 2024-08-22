package Microservice.dashboard_service.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import Microservice.dashboard_service.consumer.BillingConsumer;
import Microservice.dashboard_service.consumer.EmailConsumer;
import Microservice.dashboard_service.consumer.EnrollmentStatusConsumer;
import Microservice.dashboard_service.entity.BillingStatus;
import Microservice.dashboard_service.entity.EmailStatus;
import Microservice.dashboard_service.entity.EnrollmentStatus;
import Microservice.dashboard_service.repository.BillingStatusRepository;
import Microservice.dashboard_service.repository.EmailStatusRepository;
import Microservice.dashboard_service.repository.EnrollmentStatusRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class ScheduledTask {

	private BillingConsumer billlingConsumer;
	private EmailConsumer emailConsumer;
	private EnrollmentStatusConsumer enrollmentConsumer;
	private BillingStatusRepository billingRepo;
	private EmailStatusRepository emailRepo;
	private EnrollmentStatusRepository enrollmentRepo;

	@Autowired
	private RedisTemplate<String, EmailStatus> redisTemplate;
	private static final String REDIS_LIST_KEY_EMAIL = "emailStatusList";
	private static final String REDIS_LIST_KEY_BILLING = "billingStatusList";
	private static final String REDIS_LIST_KEY_ENROLL = "enrollStatusList";

	@Scheduled(fixedDelay = 60000)
	public void insertBillingStatus() {
		List<BillingStatus> billingStatusList = billlingConsumer.getBillingStatusList();
		log.info("Consumed From Redis:{}",billingStatusList);	
		billingRepo.saveAll(billingStatusList);
		log.info("Billing status Update Data to be inserted: {}", billingStatusList);
		redisTemplate.delete(REDIS_LIST_KEY_BILLING);
	}

	@Scheduled(fixedDelay = 60000)
	public void insertEmailStatus() {
		List<EmailStatus> emailStatuses = emailConsumer.getEmailStatusList();
		log.info("Consumed From Redis:{}",emailStatuses);
		emailRepo.saveAll(emailStatuses);
		log.info("Billing status Update Data to be inserted: {}", emailStatuses);
		redisTemplate.delete(REDIS_LIST_KEY_EMAIL);
	}

	@Scheduled(fixedDelay = 60000)
	public void insertEnrollmentStatus() {
		List<EnrollmentStatus> enrollmentStatusList = enrollmentConsumer.getEnrollmentStatusList();
		log.info("Consumed From Redis:{}",enrollmentStatusList);
		enrollmentRepo.saveAll(enrollmentStatusList);
		log.info("Enrollment Status Update Data to be inserted: {}", enrollmentStatusList);
		redisTemplate.delete(REDIS_LIST_KEY_ENROLL);
	}
}

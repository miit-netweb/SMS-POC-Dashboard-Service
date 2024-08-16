package Microservice.dashboard_service.scheduler;

import java.util.List;

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
	private static final String LOG_MESSAGE = "Email Updates Data to be inserted: {}";

	@Scheduled(fixedDelay = 60000)
	public void insertBillingStatus() {
		List<BillingStatus> billingStatusList = billlingConsumer.getBillingStatusList();
		billingRepo.saveAll(billingStatusList);
		log.info(LOG_MESSAGE, billingStatusList);
		billlingConsumer.getBillingStatusList().clear();
	}

	@Scheduled(fixedDelay = 6000)
	public void insertEmailStatus() {
		List<EmailStatus> emailStatusList = emailConsumer.getEmailStatusList();
		emailRepo.saveAll(emailStatusList);
		log.info(LOG_MESSAGE, emailStatusList);
		emailConsumer.getEmailStatusList().clear();
	}

	@Scheduled(fixedDelay = 60000)
	public void insertEnrollmentStatus() {
		List<EnrollmentStatus> enrollmentStatusList = enrollmentConsumer.getEnrollmentStatusList();
		enrollmentRepo.saveAll(enrollmentStatusList);
		log.info(LOG_MESSAGE, enrollmentStatusList);
		enrollmentConsumer.getEnrollmentStatusList().clear();
	}
}

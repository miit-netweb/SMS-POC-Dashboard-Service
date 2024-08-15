package Microservice.dashboard_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Microservice.dashboard_service.entity.EmailStatus;

public interface EmailStatusRepository extends JpaRepository<EmailStatus, Long>{

}

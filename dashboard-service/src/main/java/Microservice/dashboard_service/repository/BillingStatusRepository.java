package Microservice.dashboard_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Microservice.dashboard_service.entity.BillingStatus;

public interface BillingStatusRepository extends JpaRepository<BillingStatus, Long> {

}

package Microservice.dashboard_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Microservice.dashboard_service.entity.EnrollmentStatus;

public interface EnrollmentStatusRepository extends JpaRepository<EnrollmentStatus, Long> {

}

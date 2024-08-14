package Microservice.dashboard_service.dto;

import java.time.LocalDate;

public class BillingStatus {
    private String timeStamp;
    private String status;

    public BillingStatus() {
    }

    public BillingStatus(String timeStamp, String status) {
        this.timeStamp = timeStamp;
        this.status = status;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "EmailStatus{" +
                "timeStamp=" + timeStamp +
                ", status='" + status + '\'' +
                '}';
    }
}

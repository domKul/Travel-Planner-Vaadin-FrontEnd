package com.example.travelplannervaadinfrontend.complaint;

import java.time.LocalDateTime;

public class ComplaintDTOCreate {

    private final String title;

    private final String description;

    private final LocalDateTime complaintDate;

    private final String status;
    private final Long customerId;

    public ComplaintDTOCreate(String title, String description, LocalDateTime complaintDate, String status, long customerId) {
        this.title = title;
        this.description = description;
        this.complaintDate = LocalDateTime.now();
        this.status = status;
        this.customerId = customerId;
    }

    public long getCustomer() {
        return customerId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getComplaintDate() {
        return complaintDate;
    }

    public String getStatus() {
        return status;
    }


    public long getCustomerId() {
        return customerId;
    }


}

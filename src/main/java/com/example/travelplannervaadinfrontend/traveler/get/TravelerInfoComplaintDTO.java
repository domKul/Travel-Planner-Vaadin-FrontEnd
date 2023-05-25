package com.example.travelplannervaadinfrontend.traveler.get;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TravelerInfoComplaintDTO {
    @JsonProperty("complaintId")
    private Long complaintId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("complaintDate")
    private String complaintDate;
    @JsonProperty("status")
    private String status;

    @JsonProperty("complaintId")
    public Long getComplaintId() {
        return complaintId;
    }

    @JsonProperty("complaintId")
    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("complaintDate")
    public String getComplaintDate() {
        return complaintDate;
    }

    @JsonProperty("complaintDate")
    public void setComplaintDate(String complaintDate) {
        this.complaintDate = complaintDate;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }
}

package com.example.travelplannervaadinfrontend.booking.create;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingDTOCreate {
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("endDate")
    private String endDate;
    @JsonProperty("customerId")
    private Long customerId;
    @JsonProperty("hotelId")
    private Long hotelId;

    public BookingDTOCreate(String startDate, String endDate, Long customerId, Long hotelId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerId = customerId;
        this.hotelId = hotelId;
    }

    @JsonProperty("startDate")
    public String getStartDate() {
        return startDate;
    }

    @JsonProperty("startDate")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @JsonProperty("endDate")
    public String getEndDate() {
        return endDate;
    }

    @JsonProperty("endDate")
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @JsonProperty("customerId")
    public Long getCustomerId() {
        return customerId;
    }

    @JsonProperty("customerId")
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @JsonProperty("hotelId")
    public Long getHotelId() {
        return hotelId;
    }

    @JsonProperty("hotelId")
    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }
}

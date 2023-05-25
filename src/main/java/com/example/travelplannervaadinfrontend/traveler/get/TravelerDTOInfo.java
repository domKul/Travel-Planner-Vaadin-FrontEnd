package com.example.travelplannervaadinfrontend.traveler.get;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TravelerDTOInfo {
    @JsonProperty("customerId")
    private Long customerId;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("birthdate")
    private String birthdate;
    @JsonProperty("country")
    private String country;
    @JsonProperty("city")
    private String city;
    @JsonProperty("streetName")
    private String streetName;
    @JsonProperty("postalCode")
    private String postalCode;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phoneNumber")
    private Long phoneNumber;
    @JsonProperty("complaints")
    private List<TravelerInfoComplaintDTO> complaints;

    @JsonProperty("customerId")
    public Long getCustomerId() {
        return customerId;
    }

    @JsonProperty("customerId")
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("birthdate")
    public String getBirthdate() {
        return birthdate;
    }

    @JsonProperty("birthdate")
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("streetName")
    public String getStreetName() {
        return streetName;
    }

    @JsonProperty("streetName")
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    @JsonProperty("postalCode")
    public String getPostalCode() {
        return postalCode;
    }

    @JsonProperty("postalCode")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("phoneNumber")
    public Long getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty("phoneNumber")
    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("complaints")
    public List<TravelerInfoComplaintDTO> getComplaints() {
        return complaints;
    }

    @JsonProperty("complaints")
    public void setComplaints(List<TravelerInfoComplaintDTO> complaints) {
        this.complaints = complaints;
    }
}

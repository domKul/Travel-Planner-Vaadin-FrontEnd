package com.example.travelplannervaadinfrontend.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class CustomerDTO {
    @JsonProperty("firstName")
    @NotNull
    private String firstName;
    @JsonProperty("lastName")
    @NotNull
    private String lastName;
    @JsonProperty("birthdate")
    @NotNull
    private Date birthdate;
    @JsonProperty("country")
    @NotNull
    private String country;
    @JsonProperty("city")
    @NotNull
    private String city;

    @JsonProperty("streetName")
    @NotNull
    private String streetName;
    @JsonProperty("postalCode")
    @NotNull
    private String postalCode;
    @JsonProperty("email")
    @NotNull
    private String email;
    @JsonProperty("phoneNumber")
    @NotNull
    private int phoneNumber;


    public CustomerDTO() {
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }


    public void setCountry(String country) {
        this.country = country;
    }


    public void setCity(String city) {
        this.city = city;
    }


    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }


    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getEmail() {
        return email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }
}
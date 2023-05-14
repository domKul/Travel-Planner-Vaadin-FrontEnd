package com.example.travelplannervaadinfrontend.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class CustomerDTO {
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("birthdate")
    private Date birthdate;
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
    private int phoneNumber;


    public CustomerDTO(String firstName, String lastName, Date birthdate, String country, String city,
                       String streetName, String postalCode, String email, int phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.country = country;
        this.city = city;
        this.streetName = streetName;
        this.postalCode = postalCode;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public CustomerDTO() {
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }



    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    public void setBirthdate(Date birthdate) {
        this.birthdate = (Date) birthdate;
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
}
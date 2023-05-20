package com.example.travelplannervaadinfrontend.traveler.save;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TravelerDTO {
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


    public TravelerDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
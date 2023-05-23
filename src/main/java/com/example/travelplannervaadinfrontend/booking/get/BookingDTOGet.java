package com.example.travelplannervaadinfrontend.booking.get;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
public class BookingDTOGet {
    @JsonProperty("bookingId")
    private Long bookingId;
    @JsonProperty("bookTime")
    private String bookTime;
    @JsonProperty("customerId")
    private Long customerId;
    @JsonProperty("customerFirstName")
    private String customerFirstName;
    @JsonProperty("customerLastName")
    private String customerLastName;
    @JsonProperty("birthDate")
    private String birthDate;
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
    @JsonProperty("hotelName")
    private String hotelName;
    @JsonProperty("hotelId")
    private long hotelId;
    @JsonProperty("hotelPrice")
    private String hotelPrice;
    @JsonProperty("startBooking")
    private String startBooking;
    @JsonProperty("endBooking")
    private String endBooking;
    @JsonProperty("currency")
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getStartBooking() {
        return startBooking;
    }

    public void setStartBooking(String startBooking) {
        this.startBooking = startBooking;
    }

    public String getEndBooking() {
        return endBooking;
    }

    public void setEndBooking(String endBooking) {
        this.endBooking = endBooking;
    }

    @JsonProperty("bookTime")
    public String getBookTime() {
        return bookTime;
    }

    @JsonProperty("bookTime")
    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    @JsonProperty("customerId")
    public Long getCustomerId() {
        return customerId;
    }

    @JsonProperty("customerId")
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @JsonProperty("customerFirstName")
    public String getCustomerFirstName() {
        return customerFirstName;
    }

    @JsonProperty("customerFirstName")
    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    @JsonProperty("customerLastName")
    public String getCustomerLastName() {
        return customerLastName;
    }

    @JsonProperty("customerLastName")
    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    @JsonProperty("birthDate")
    public String getBirthDate() {
        return birthDate;
    }

    @JsonProperty("birthDate")
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
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

    @JsonProperty("hotelName")
    public String getHotelName() {
        return hotelName;
    }

    @JsonProperty("tourPrice")
    public void setTourPrice(String hotelName) {
        this.hotelName = hotelName;
    }

    @JsonProperty("hotelId")
    public Long getHotelId() {
        return hotelId;
    }

    @JsonProperty("hotelId")
    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    @JsonProperty("hotelPrice")
    public String getHotelPrice() {
        return hotelPrice;
    }

    @JsonProperty("hotelPrice")
    public void setHotelPrice(String hotelPrice) {
        this.hotelPrice = hotelPrice;
    }
}

package com.example.travelplannervaadinfrontend.hotel.get;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HotelDTOGet {

    @JsonProperty("hotelId")
    private long hotelId;

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("countryCode")
    private String countryCode;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("hotelPrice")
    private int hotelPrice;


    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getHotelPrice() {
        return hotelPrice;
    }

    public void setHotelPrice(int hotelPrice) {
        this.hotelPrice = hotelPrice;
    }
}

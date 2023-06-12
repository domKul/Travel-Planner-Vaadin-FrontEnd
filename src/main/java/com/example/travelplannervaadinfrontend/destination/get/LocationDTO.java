package com.example.travelplannervaadinfrontend.destination.get;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationDTO {
    @JsonProperty("label")
    private String label;
    @JsonProperty("region")
    private String region;
    @JsonProperty("dest_id")
    private String dest_id;

    @JsonProperty("dest_type")
    private String destination_type;
    @JsonProperty("name")
    private String name;
    @JsonProperty("country")
    private String country;

    @JsonProperty("hotels")
    private Integer hotels;
    @JsonProperty("timezone")
    private String timezone;

    public LocationDTO(String label, String region, String dest_id,String destination_type, String name, String country, Integer hotels, String timezone) {
        this.label = label;
        this.region = region;
        this.dest_id = dest_id;
        this.destination_type = destination_type;
        this.name = name;
        this.country = country;
        this.hotels = hotels;
        this.timezone = timezone;
    }

    public LocationDTO() {
    }

    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    @JsonProperty("region")
    public String getRegion() {
        return region;
    }

    @JsonProperty("region")
    public void setRegion(String region) {
        this.region = region;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("hotels")
    public Integer getHotels() {
        return hotels;
    }

    @JsonProperty("dest_id")
    public String getDest_id() {
        return dest_id;
    }

    @JsonProperty("timezone")
    public String getTimezone() {
        return timezone;
    }

    @JsonProperty("dest_type")
    public String getDestination_type() {
        return destination_type;
    }
}

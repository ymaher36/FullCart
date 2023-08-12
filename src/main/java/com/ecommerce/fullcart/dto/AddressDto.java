package com.ecommerce.fullcart.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


public class AddressDto {
    @Positive(message = "District is required")
    private int districtId;

    @NotNull(message = "Street is required")
    @Size(min = 1, message = "Street is required")
    private String street;
    @NotNull(message = "Building is required")
    @Size(min = 1, message = "Building is required")
    private String building;
    private String floor;
    private String apt;
    private String other;


    public AddressDto() {
    }

    public AddressDto(int districtId,
                      @NotNull(message = "Street is required") String street,
                      @NotNull(message = "Building is required") String building,
                      String floor,
                      String apt,
                      String other) {
        this.districtId = districtId;
        this.street = street;
        this.building = building;
        this.floor = floor;
        this.apt = apt;
        this.other = other;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getApt() {
        return apt;
    }

    public void setApt(String apt) {
        this.apt = apt;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    @Override
    public String toString() {
        return "AddressDto{" +
                "districtId=" + districtId +
                ", street='" + street + '\'' +
                ", building='" + building + '\'' +
                ", floor='" + floor + '\'' +
                ", apt='" + apt + '\'' +
                ", other='" + other + '\'' +
                '}';
    }
}

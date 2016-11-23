package com.app.theweatherapp.api;

/**
 * Created by beyeta on 11/19/16.
 */

public class ApiResourceLocation {
    private Double[] longitude;
    private Double[] latitude;
    private String[] address;
    private String[] city;
    private String[] country;

    public Double[] getLongitude() {
        return longitude;
    }

    public void setLongitude(Double[] longitude) {
        this.longitude = longitude;
    }

    public Double[] getLatitude() {
        return latitude;
    }

    public void setLatitude(Double[] latitude) {
        this.latitude = latitude;
    }

    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] address) {
        this.address = address;
    }

    public String[] getCity() {
        return city;
    }

    public void setCity(String[] city) {
        this.city = city;
    }

    public String[] getCountry() {
        return country;
    }

    public void setCountry(String[] country) {
        this.country = country;
    }
}

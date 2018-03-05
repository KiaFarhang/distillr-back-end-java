package com.kiafarhang.distillr.yelp;

import com.google.gson.*;

public class YelpBusinessLocation {
    private String city;
    private String country;
    private String address2;
    private String address3;
    private String state;
    private String address1;
    private String zip_code;

    public YelpBusinessLocation(String city, String country, String address2, String address3, String state,
            String address1, String zip_code) {
        this.city = city;
        this.country = country;
        this.address2 = address2;
        this.address3 = address3;
        this.state = state;
        this.address1 = address1;
        this.zip_code = zip_code;
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public String getAddress2() {
        return this.address2;
    }

    public String getAddress3() {
        return this.address3;
    }

    public String getState() {
        return this.state;
    }

    public String getAddress1() {
        return this.address1;
    }

    public String getZipCode() {
        return this.zip_code;
    }

    public static YelpBusinessLocation buildYelpBusinessLocationFromJSON(String json) {
        Gson gson = new Gson();
        YelpBusinessLocation businessLocation = gson.fromJson(json, YelpBusinessLocation.class);
        return businessLocation;
    }
}

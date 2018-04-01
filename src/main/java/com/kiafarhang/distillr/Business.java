package com.kiafarhang.distillr;

import com.kiafarhang.distillr.Location;

public class Business {
    private String name;
    private String imgURL;
    private boolean isClosed;
    private String cost;
    private String phoneNumber;
    private String address;
    private String yelpURL;
    private Location coordinates;
    private double rating;

    public Business(String name, String imgURL, boolean isClosed, String cost, String phoneNumber, String address,
            String yelpURL, Location coordinates, double rating) {
        this.name = name;
        this.imgURL = imgURL;
        this.isClosed = isClosed;
        this.cost = cost;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.yelpURL = yelpURL;
        this.coordinates = coordinates;
        this.rating = rating;
    }

}
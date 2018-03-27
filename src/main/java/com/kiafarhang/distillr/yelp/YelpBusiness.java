package com.kiafarhang.distillr.yelp;

import com.kiafarhang.distillr.yelp.YelpBusinessLocation;
import com.kiafarhang.distillr.Location;
import com.kiafarhang.distillr.yelp.HasPriceAndDistance;

import com.google.gson.*;

public class YelpBusiness implements HasPriceAndDistance {
    private double rating;
    private String price;
    private String phone;
    private String id;
    private boolean is_closed;
    private Category[] categories;
    private double review_count;
    private String name;
    private String url;
    private Location coordinates;
    private String image_url;
    private YelpBusinessLocation location;
    private double distance;
    private String[] transactions;

    private class Category {
        private String alias;
        private String title;
    }

    public YelpBusiness(double rating, String price, String phone, String id, boolean is_closed, Category[] categories,
            double review_count, String name, String url, Location coordinates, String image_url,
            YelpBusinessLocation location, double distance, String[] transactions) {
        this.rating = rating;
        this.price = price;
        this.phone = phone;
        this.id = id;
        this.is_closed = is_closed;
        this.categories = categories;
        this.review_count = review_count;
        this.name = name;
        this.url = url;
        this.coordinates = coordinates;
        this.image_url = image_url;
        this.location = location;
        this.distance = distance;
        this.transactions = transactions;
    }

    public static YelpBusiness buildYelpBusinessFromJSON(String json) {
        Gson gson = new Gson();
        YelpBusiness business = gson.fromJson(json, YelpBusiness.class);
        return business;
    }

    public double getRating() {
        return rating;
    }

    public String getPrice() {
        return price;
    }

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }

    public boolean isClosed() {
        return is_closed;
    }

    public Category[] getCategories() {
        return categories;
    }

    public double getReview_count() {
        return review_count;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Location getCoordinates() {
        return coordinates;
    }

    public String getImage_url() {
        return image_url;
    }

    public YelpBusinessLocation getLocation() {
        return location;
    }

    public double getDistance() {
        return distance;
    }

    public String[] getTransactions() {
        return transactions;
    }
}
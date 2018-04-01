package com.kiafarhang.distillr;

import com.kiafarhang.distillr.Business;

public class Trip {
    private Business business;
    private int minutes;
    private String cost;

    public Trip(Business business, int minutes, String cost) {
        this.business = business;
        this.minutes = minutes;
        this.cost = cost;
    }
}
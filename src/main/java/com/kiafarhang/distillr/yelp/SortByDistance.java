package com.kiafarhang.distillr.yelp;

import com.kiafarhang.distillr.yelp.HasPriceAndDistance;

import java.util.Comparator;

public class SortByDistance implements Comparator<HasPriceAndDistance> {
    public int compare(HasPriceAndDistance a, HasPriceAndDistance b) {
        return (int) a.getDistance() - (int) b.getDistance();
    }
}
package com.kiafarhang.distillr.yelp;

import com.kiafarhang.distillr.yelp.YelpBusiness;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class YelpUtilities {

    // Filtering stolen from
    // https://www.mkyong.com/java8/java-8-streams-filter-examples/

    public static List<YelpBusiness> removeClosedBusinesses(List<YelpBusiness> businesses) {
        List<YelpBusiness> filtered = businesses.stream().filter(business -> !business.isClosed())
                .collect(Collectors.toList());

        return filtered;
    }

    // // Yelp gives guidance as to how much a dollar sign is worth
    // // https://www.quora.com/How-are-dollar-signs-calculated-on-Yelp-and-who-calculates-them
    public static String generateMaxDollarSignsFromMoney(double money) {
        if (money > 61) {
            return "$$$$";
        } else if (money > 31) {
            return "$$$";
        } else if (money > 11) {
            return "$$";
        }
        return "$";
    }
}
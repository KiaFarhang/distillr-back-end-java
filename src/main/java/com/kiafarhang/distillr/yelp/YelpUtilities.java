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
}
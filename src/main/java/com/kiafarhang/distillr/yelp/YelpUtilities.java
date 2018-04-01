package com.kiafarhang.distillr.yelp;

import com.kiafarhang.distillr.yelp.YelpBusiness;
import com.kiafarhang.distillr.yelp.HasPriceAndDistance;
import com.kiafarhang.distillr.yelp.SortByDistance;
import com.kiafarhang.distillr.yelp.CategoryTimeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

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

    public static String generateRangeFromDollarSigns(String dollarSigns) {
        if (dollarSigns == "$$$$") {
            return "+$64";
        } else if (dollarSigns == "$$$") {
            return "$34-$63";
        } else if (dollarSigns == "$$") {
            return "$13-$33";
        } else {
            return "$3-$12";
        }
    }

    public static Integer calculateTimeNeeded(String alias, String price) {
        int time = 0;

        Map<String, Integer> categoryTimeMap = CategoryTimeMap.getCategoryTimeMap();

        if (categoryTimeMap.get(alias) != null) {
            if (price == "$$$$") {
                time += 70;
            } else if (price == "$$$") {
                time += 45;
            } else if (price == "$$") {
                time += 30;
            } else {
                time += 20;
            }

            time += categoryTimeMap.get(alias);

            // We figure 30 minutes for a there-and-back bus ride.

            time += 30;
        }

        return time;
    }

    public static List<YelpBusiness> removeExpensiveBusinesses(List<YelpBusiness> businesses, double money) {
        String maxDollarSigns = generateMaxDollarSignsFromMoney(money);
        List<YelpBusiness> filtered = businesses.stream()
                .filter(business -> business.getPrice().contains(maxDollarSigns)).collect(Collectors.toList());

        return filtered;

    }

    public static List<? extends HasPriceAndDistance> sortByPriceAndDistance(
            List<? extends HasPriceAndDistance> objects) {

        ArrayList<ArrayList<HasPriceAndDistance>> separateArraysByPrice = new ArrayList<ArrayList<HasPriceAndDistance>>();
        List<HasPriceAndDistance> oneDollars = objects.stream().filter(object -> object.getPrice() == "$")
                .collect(Collectors.toList());
        List<HasPriceAndDistance> twoDollars = objects.stream().filter(object -> object.getPrice() == "$$")
                .collect(Collectors.toList());
        List<HasPriceAndDistance> threeDollars = objects.stream().filter(object -> object.getPrice() == "$$$")
                .collect(Collectors.toList());
        List<HasPriceAndDistance> fourDollars = objects.stream().filter(object -> object.getPrice() == "$$$$")
                .collect(Collectors.toList());

        separateArraysByPrice.add(new ArrayList<HasPriceAndDistance>(oneDollars));
        separateArraysByPrice.add(new ArrayList<HasPriceAndDistance>(twoDollars));
        separateArraysByPrice.add(new ArrayList<HasPriceAndDistance>(threeDollars));
        separateArraysByPrice.add(new ArrayList<HasPriceAndDistance>(fourDollars));

        ArrayList<HasPriceAndDistance> toReturn = new ArrayList<HasPriceAndDistance>();

        for (ArrayList<HasPriceAndDistance> arrayList : separateArraysByPrice) {
            arrayList.sort(new SortByDistance());
            toReturn.addAll(arrayList);
        }

        return toReturn;
    }

}
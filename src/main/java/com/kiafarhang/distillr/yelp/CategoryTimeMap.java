package com.kiafarhang.distillr.yelp;

import java.util.Map;
import java.util.HashMap;

public class CategoryTimeMap {

    // This map contains completely arbitrary mappings from Yelp activity types
    // to the amount of minutes one of those activities might take.

    public static Map<String, Integer> getCategoryTimeMap() {
        Map<String, Integer> map = new HashMap<String, Integer>();

        map.put("active", 120);
        map.put("arts", 120);
        map.put("coffee", 30);
        map.put("food", 30);
        map.put("pets", 90);
        map.put("professional", 60);
        map.put("restaurants", 45);
        map.put("shopping", 90);
        map.put("nighlife", 180);
        map.put("pizza", 5);
        map.put("italian", 5);
        map.put("chinese", 5);
        map.put("vietnamese", 5);
        map.put("thai", 5);
        map.put("mexican", 5);
        map.put("french", 5);
        map.put("german", 5);
        map.put("belgian", 5);
        map.put("mediterranean", 5);
        map.put("greek", 5);
        map.put("vegan", 5);
        map.put("vegetarian", 5);
        map.put("gluten_free", 5);
        map.put("trademerican", 5);
        map.put("desserts", 5);
        map.put("bakeries", 5);
        map.put("salad", 5);
        map.put("burgers", 5);
        map.put("delis", 5);
        map.put("icecream", 5);
        map.put("musicvenues", 120);
        map.put("grocery", 20);
        map.put("drugstores", 20);
        map.put("dptstores", 60);
        map.put("shoes", 45);
        map.put("theatre", 120);
        map.put("sportsbars", 120);
        map.put("cocktailbars", 120);
        map.put("pubs", 120);
        map.put("bars", 120);
        map.put("breweries", 120);
        map.put("lounges", 120);

        return map;

    }

}
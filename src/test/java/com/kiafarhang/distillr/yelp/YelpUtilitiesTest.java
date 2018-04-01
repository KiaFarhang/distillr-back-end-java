package com.kiafarhang.distillr.yelp;

import com.kiafarhang.distillr.yelp.YelpUtilities;
import com.kiafarhang.distillr.yelp.YelpBusiness;
import com.kiafarhang.distillr.yelp.HasPriceAndDistance;

import java.io.IOException;
import java.nio.file.*;
import java.util.Collection;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

import com.google.gson.*;

public class YelpUtilitiesTest {
    Gson gson = new Gson();

    @Test
    public void filtersOutClosedBusinesses() throws IOException {
        String json = new String(
                Files.readAllBytes(Paths.get("src/test/java/com/kiafarhang/distillr/yelp/yelp-businesses.json")));
        YelpBusiness[] businesses = gson.fromJson(json, YelpBusiness[].class);
        List<YelpBusiness> asList = Arrays.asList(businesses);

        List<YelpBusiness> filteredBusinesses = YelpUtilities.removeClosedBusinesses(asList);

        // One of the three businesses in the file is closed
        assertEquals(filteredBusinesses.size(), 2, 0);
    }

    @Test
    public void convertsMoneyToDollarSigns() {
        assertEquals(YelpUtilities.generateMaxDollarSignsFromMoney(65), "$$$$");
        assertEquals(YelpUtilities.generateMaxDollarSignsFromMoney(45), "$$$");
        assertEquals(YelpUtilities.generateMaxDollarSignsFromMoney(25), "$$");
        assertEquals(YelpUtilities.generateMaxDollarSignsFromMoney(5), "$");
    }

    @Test
    public void convertsDollarSignsToStrings() {
        assertEquals(YelpUtilities.generateRangeFromDollarSigns("$$$$"), "+$64");
        assertEquals(YelpUtilities.generateRangeFromDollarSigns("$$$"), "$34-$63");
        assertEquals(YelpUtilities.generateRangeFromDollarSigns("$$"), "$13-$33");
        assertEquals(YelpUtilities.generateRangeFromDollarSigns("$"), "$3-$12");
    }

    @Test
    public void returnsZeroMinutesIfActivityNotInMap() {
        // "pokemon_hunting" is not in the Map at src/...yelp/CategoryTimeMap, so this method will return 0

        assertEquals(YelpUtilities.calculateTimeNeeded("pokemon_hunting", "$"), 0, 0);
    }

    @Test
    public void returnsCorrectMinutesIfActivityInMap() {

        assertEquals(YelpUtilities.calculateTimeNeeded("icecream", "$"), 55, 0);
        assertEquals(YelpUtilities.calculateTimeNeeded("bars", "$$$$"), 220, 0);
        assertEquals(YelpUtilities.calculateTimeNeeded("shoes", "foobar"), 95, 0);
    }

    @Test
    public void filtersOutExpensiveRestaurants() throws IOException {
        String json = new String(
                Files.readAllBytes(Paths.get("src/test/java/com/kiafarhang/distillr/yelp/yelp-businesses.json")));
        YelpBusiness[] businesses = gson.fromJson(json, YelpBusiness[].class);
        List<YelpBusiness> asList = Arrays.asList(businesses);

        List<YelpBusiness> filteredBusinesses = YelpUtilities.removeExpensiveBusinesses(asList, 25);

        // One of the three businesses in the file's price is "$$$"
        assertEquals(filteredBusinesses.size(), 2, 0);
    }

    private class PriceAndDistanceDummy implements HasPriceAndDistance {
        String price;
        double distance;

        public PriceAndDistanceDummy(String price, double distance) {
            this.price = price;
            this.distance = distance;
        }

        public String getPrice() {
            return this.price;
        }

        public double getDistance() {
            return this.distance;
        }
    }

    @Test
    public void sortsByPrice() {
        PriceAndDistanceDummy a = new PriceAndDistanceDummy("$$$$", 10);
        PriceAndDistanceDummy b = new PriceAndDistanceDummy("$$$", 10);
        PriceAndDistanceDummy c = new PriceAndDistanceDummy("$$", 10);
        PriceAndDistanceDummy d = new PriceAndDistanceDummy("$", 10);

        ArrayList<PriceAndDistanceDummy> list = new ArrayList<PriceAndDistanceDummy>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);

        @SuppressWarnings("unchecked")
        List<PriceAndDistanceDummy> sorted = (List<PriceAndDistanceDummy>) YelpUtilities.sortByPriceAndDistance(list);

        assertEquals(sorted.get(0).getPrice(), "$");
        assertEquals(sorted.get(1).getPrice(), "$$");
        assertEquals(sorted.get(2).getPrice(), "$$$");
        assertEquals(sorted.get(3).getPrice(), "$$$$");

    }

    @Test
    public void sortsByDistance() {
        PriceAndDistanceDummy a = new PriceAndDistanceDummy("$$$$", 100);
        PriceAndDistanceDummy b = new PriceAndDistanceDummy("$$$$", 99);
        PriceAndDistanceDummy c = new PriceAndDistanceDummy("$$$$", 98);
        PriceAndDistanceDummy d = new PriceAndDistanceDummy("$$$$", 97);

        ArrayList<PriceAndDistanceDummy> list = new ArrayList<PriceAndDistanceDummy>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);

        @SuppressWarnings("unchecked")
        List<PriceAndDistanceDummy> sorted = (List<PriceAndDistanceDummy>) YelpUtilities.sortByPriceAndDistance(list);

        assertEquals(sorted.get(0).getDistance(), 97, 0);
        assertEquals(sorted.get(1).getDistance(), 98, 0);
        assertEquals(sorted.get(2).getDistance(), 99, 0);
        assertEquals(sorted.get(3).getDistance(), 100, 0);

    }

    @Test
    public void sortsByPriceAndDistance() {
        PriceAndDistanceDummy a = new PriceAndDistanceDummy("$$$$", 100);
        PriceAndDistanceDummy b = new PriceAndDistanceDummy("$$$$", 99);
        PriceAndDistanceDummy c = new PriceAndDistanceDummy("$$", 98);
        PriceAndDistanceDummy d = new PriceAndDistanceDummy("$$", 97);
        PriceAndDistanceDummy e = new PriceAndDistanceDummy("$", 500);

        ArrayList<PriceAndDistanceDummy> list = new ArrayList<PriceAndDistanceDummy>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);

        @SuppressWarnings("unchecked")
        List<PriceAndDistanceDummy> sorted = (List<PriceAndDistanceDummy>) YelpUtilities.sortByPriceAndDistance(list);

        HasPriceAndDistance first = sorted.get(0);
        HasPriceAndDistance second = sorted.get(1);
        HasPriceAndDistance third = sorted.get(2);
        HasPriceAndDistance fourth = sorted.get(3);
        HasPriceAndDistance fifth = sorted.get(4);

        assertEquals(first.getPrice(), "$");
        assertEquals(first.getDistance(), 500, 0);

        assertEquals(second.getPrice(), "$$");
        assertEquals(second.getDistance(), 97, 0);

        assertEquals(third.getPrice(), "$$");
        assertEquals(third.getDistance(), 98, 0);

        assertEquals(fourth.getPrice(), "$$$$");
        assertEquals(fourth.getDistance(), 99, 0);

        assertEquals(fifth.getPrice(), "$$$$");
        assertEquals(fifth.getDistance(), 100, 0);

    }
}

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

        // One of the two businesses in the file is closed
        assertEquals(filteredBusinesses.size(), 1, 0);
    }

    @Test
    public void convertsMoneyToDollarSigns() {
        assertEquals(YelpUtilities.generateMaxDollarSignsFromMoney(65), "$$$$");
        assertEquals(YelpUtilities.generateMaxDollarSignsFromMoney(45), "$$$");
        assertEquals(YelpUtilities.generateMaxDollarSignsFromMoney(25), "$$");
        assertEquals(YelpUtilities.generateMaxDollarSignsFromMoney(5), "$");
    }

    @Test
    public void filtersOutExpensiveRestaurants() throws IOException {
        String json = new String(
                Files.readAllBytes(Paths.get("src/test/java/com/kiafarhang/distillr/yelp/yelp-businesses.json")));
        YelpBusiness[] businesses = gson.fromJson(json, YelpBusiness[].class);
        List<YelpBusiness> asList = Arrays.asList(businesses);

        List<YelpBusiness> filteredBusinesses = YelpUtilities.removeExpensiveBusinesses(asList, 25);

        // One of the two businesses in the file's price is "$$$"
        assertEquals(filteredBusinesses.size(), 1, 0);
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

        ArrayList<HasPriceAndDistance> list = new ArrayList<HasPriceAndDistance>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);

        List<HasPriceAndDistance> sorted = YelpUtilities.sortByPriceAndDistance(list);

        assertEquals(sorted.get(0).getPrice(), "$");
        assertEquals(sorted.get(1).getPrice(), "$$");
        assertEquals(sorted.get(2).getPrice(), "$$$");
        assertEquals(sorted.get(3).getPrice(), "$$$$");

    }

    public void sortsByDistance() {
        PriceAndDistanceDummy a = new PriceAndDistanceDummy("$$$$", 100);
        PriceAndDistanceDummy b = new PriceAndDistanceDummy("$$$$", 99);
        PriceAndDistanceDummy c = new PriceAndDistanceDummy("$$$$", 98);
        PriceAndDistanceDummy d = new PriceAndDistanceDummy("$$$$", 97);

        ArrayList<HasPriceAndDistance> list = new ArrayList<HasPriceAndDistance>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);

        List<HasPriceAndDistance> sorted = YelpUtilities.sortByPriceAndDistance(list);

        assertEquals(list.get(0).getDistance(), 97, 0);
        assertEquals(list.get(1).getDistance(), 98, 0);
        assertEquals(list.get(2).getDistance(), 99, 0);
        assertEquals(list.get(3).getDistance(), 100, 0);

    }

    public void sortsByPriceAndDistance() {
        PriceAndDistanceDummy a = new PriceAndDistanceDummy("$$$$", 100);
        PriceAndDistanceDummy b = new PriceAndDistanceDummy("$$$$", 99);
        PriceAndDistanceDummy c = new PriceAndDistanceDummy("$$", 98);
        PriceAndDistanceDummy d = new PriceAndDistanceDummy("$$", 97);
        PriceAndDistanceDummy e = new PriceAndDistanceDummy("$", 500);

        ArrayList<HasPriceAndDistance> list = new ArrayList<HasPriceAndDistance>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);

        List<HasPriceAndDistance> sorted = YelpUtilities.sortByPriceAndDistance(list);

        HasPriceAndDistance first = list.get(0);
        HasPriceAndDistance second = list.get(1);
        HasPriceAndDistance third = list.get(2);
        HasPriceAndDistance fourth = list.get(3);
        HasPriceAndDistance fifth = list.get(4);

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

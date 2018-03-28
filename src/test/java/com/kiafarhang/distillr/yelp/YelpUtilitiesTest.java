package com.kiafarhang.distillr.yelp;

import com.kiafarhang.distillr.yelp.YelpUtilities;
import com.kiafarhang.distillr.yelp.YelpBusiness;

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
    @Test
    public void filtersOutClosedBusinesses() throws IOException {
        String json = new String(
                Files.readAllBytes(Paths.get("src/test/java/com/kiafarhang/distillr/yelp/yelp-businesses.json")));
        Gson gson = new Gson();
        YelpBusiness[] businesses = gson.fromJson(json, YelpBusiness[].class);
        List<YelpBusiness> asList = Arrays.asList(businesses);

        Collection<YelpBusiness> filteredBusinesses = YelpUtilities.removeClosedBusinesses(asList);

        assertEquals(filteredBusinesses.size(), 1, 0);
    }

    @Test
    public void convertsMoneyToDollarSigns() {
        assertEquals(YelpUtilities.generateMaxDollarSignsFromMoney(65), "$$$$");
        assertEquals(YelpUtilities.generateMaxDollarSignsFromMoney(45), "$$$");
        assertEquals(YelpUtilities.generateMaxDollarSignsFromMoney(25), "$$");
        assertEquals(YelpUtilities.generateMaxDollarSignsFromMoney(5), "$");
    }
}

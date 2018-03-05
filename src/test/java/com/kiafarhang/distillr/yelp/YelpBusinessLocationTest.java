package com.kiafarhang.distillr.yelp;

import com.kiafarhang.distillr.yelp.YelpBusinessLocation;
import com.google.gson.JsonSyntaxException;
import org.junit.Test;
import static org.junit.Assert.*;

public class YelpBusinessLocationTest {
    @Test
    public void parsesValidString() {
        String validJSON = "{\n        \"city\": \"San Francisco\",\n        \"country\": \"US\",\n        \"address2\": \"\",\n        \"address3\": \"\",\n        \"state\": \"CA\",\n        \"address1\": \"375 Valencia St\",\n        \"zip_code\": \"94103\"\n      }";
        YelpBusinessLocation businessLocation = YelpBusinessLocation.buildYelpBusinessLocationFromJSON(validJSON);
        assertEquals(businessLocation.getCity(), "San Francisco");
        assertEquals(businessLocation.getState(), "CA");
        assertEquals(businessLocation.getCountry(), "US");
    }

    @Test(expected = JsonSyntaxException.class)
    public void throwsOnInvalidString() {
        String invalidJSON = "Screaming Females";
        YelpBusinessLocation businessLocation = YelpBusinessLocation.buildYelpBusinessLocationFromJSON(invalidJSON);
    }
}
package com.kiafarhang.distillr.yelp;

import com.kiafarhang.distillr.yelp.YelpAPIQuery;
import com.kiafarhang.distillr.Location;

import java.io.UnsupportedEncodingException;

import java.util.Objects;

import org.junit.Test;
import static org.junit.Assert.*;

public class YelpApiQueryTest {
    @Test
    public void buildsQueryFromTermAndLocation() throws UnsupportedEncodingException {
        String searchTerm = "hamburgers";
        Location location = new Location(29.4786, -98.4872);
        String expected = "http://localhost:8000/v3/businesses/search?term=hamburgers&latitude=29.4786&longitude=-98.4872";
        String result = YelpAPIQuery.buildYelpQueryString(searchTerm, location);
        assertEquals(expected, result);
    }

    @Test
    public void buildsQueryFromTermAndAddress() throws UnsupportedEncodingException {
        String searchTerm = "hamburgers";
        String address = "123 Fake Street";
        String expected = "http://localhost:8000/v3/businesses/search?term=hamburgers&location=123+Fake+Street";
        String result = YelpAPIQuery.buildYelpQueryString(searchTerm, address);
        assertEquals(expected, result);
    }

    @Test
    public void escapesSearchTerms() throws UnsupportedEncodingException {
        String searchTerm = "mexican food";

        String address = "123 Fake Street";
        String expectedA = "http://localhost:8000/v3/businesses/search?term=mexican+food&location=123+Fake+Street";
        String resultA = YelpAPIQuery.buildYelpQueryString(searchTerm, address);

        Location location = new Location(29.4786, -98.4872);
        String expectedB = "http://localhost:8000/v3/businesses/search?term=mexican+food&latitude=29.4786&longitude=-98.4872";
        String resultB = YelpAPIQuery.buildYelpQueryString(searchTerm, location);

        assertEquals(expectedA, resultA);
        assertEquals(expectedB, resultB);
    }
}
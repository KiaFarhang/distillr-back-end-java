package com.kiafarhang.distillr.yelp;

import com.kiafarhang.distillr.Location;

import java.io.UnsupportedEncodingException;
import java.net.*;

public class YelpAPIQuery {
    public static String buildYelpQueryString(String searchTerm, Location location)
            throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();

        // If we're in a test environment with this variable set,
        // We want to ping localhost instead of Yelp.

        if (System.getenv("YELP_HOST") != null) {
            stringBuilder.append(System.getenv("YELP_HOST"));
        } else {
            stringBuilder.append("https://api.yelp.com");
        }

        stringBuilder.append("/v3/businesses/search?term=");
        stringBuilder.append(URLEncoder.encode(searchTerm, "UTF-8"));
        stringBuilder.append("&latitude=");
        stringBuilder.append(location.getLatitude());
        stringBuilder.append("&longitude=");
        stringBuilder.append(location.getLongitude());
        return stringBuilder.toString();
    }

    public static String buildYelpQueryString(String searchTerm, String address) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();

        // If we're in a test environment with this variable set,
        // We want to ping localhost instead of Yelp.

        if (System.getenv("YELP_HOST") != null) {
            stringBuilder.append(System.getenv("YELP_HOST"));
        } else {
            stringBuilder.append("https://api.yelp.com");
        }

        stringBuilder.append("/v3/businesses/search?term=");
        stringBuilder.append(URLEncoder.encode(searchTerm, "UTF-8"));
        stringBuilder.append("&location=");
        stringBuilder.append(URLEncoder.encode(address, "UTF-8"));
        return stringBuilder.toString();

        // https://stackoverflow.com/questions/749709/how-to-deal-with-the-urisyntaxexception
        // URL url = new URL(stringBuilder.toString());
        // URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
        // return uri.toString();
    }

    public static String getMockYelpHost() {
        return System.getenv("YELP_HOST");
    }
}
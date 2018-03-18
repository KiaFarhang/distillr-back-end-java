package com.kiafarhang.distillr.yelp;

import com.kiafarhang.distillr.Location;

import java.io.UnsupportedEncodingException;
import java.net.*;

public class YelpAPIQuery {
    public static String buildYelpQueryString(String searchTerm, Location location) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://api.yelp.com/v3/businesses/search?term=");
        stringBuilder.append(searchTerm);
        stringBuilder.append("&latitude=");
        stringBuilder.append(location.getLatitude());
        stringBuilder.append("&longitude=");
        stringBuilder.append(location.getLongitude());
        return stringBuilder.toString();
    }

    public static String buildYelpQueryString(String searchTerm, String address) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://api.yelp.com/v3/businesses/search?term=");
        stringBuilder.append(searchTerm);
        stringBuilder.append("&location=");
        stringBuilder.append(URLEncoder.encode(address, "UTF-8"));
        return stringBuilder.toString();

        // https://stackoverflow.com/questions/749709/how-to-deal-with-the-urisyntaxexception
        // URL url = new URL(stringBuilder.toString());
        // URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
        // return uri.toString();
    }
}
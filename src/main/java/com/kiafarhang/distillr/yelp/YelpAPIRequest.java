package com.kiafarhang.distillr.yelp;

import com.kiafarhang.distillr.yelp.YelpAPIQuery;
import com.kiafarhang.distillr.yelp.YelpBusiness;
import com.kiafarhang.distillr.Business;
import com.kiafarhang.distillr.Trip;
import com.kiafarhang.distillr.yelp.YelpUtilities;
import com.kiafarhang.distillr.yelp.HasPriceAndDistance;
import com.kiafarhang.distillr.yelp.YelpBusinessLocation;

import com.kiafarhang.distillr.server.UserRequest;

import com.google.gson.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;
// import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.message.BasicHeader;

public class YelpAPIRequest {
    private class YelpAPIResponse {
        YelpBusiness[] businesses;
    }

    public static YelpBusiness[] fetchYelpData(UserRequest request) throws IOException {

        String yelpEndpoint = YelpAPIQuery.buildYelpQueryString(request.getSearchTerm(), request.getLocation());

        // Get API key

        File apiKeyFile = new File("yelp-key.txt");
        Scanner fileScanner = new Scanner(apiKeyFile, "UTF-8");
        String yelpAPIKey = fileScanner.nextLine();

        BasicHeader header = new BasicHeader("Authorization", "Bearer " + yelpAPIKey);

        // Set up and execute the HTTP request

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(yelpEndpoint);
        httpGet.setHeader(header);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        // Convert the response into a string

        HttpEntity entity = response.getEntity();
        InputStream stream = entity.getContent();
        Scanner scanner = new Scanner(stream);
        scanner.useDelimiter("\\A");
        String content = scanner.hasNext() ? scanner.next() : "";

        // Close streams to prevent memory leaks

        scanner.close();
        response.close();
        EntityUtils.consume(entity);

        // Turn response into a list of YelpBusinesses

        Gson gson = new Gson();
        YelpAPIResponse apiResponse = gson.fromJson(content, YelpAPIResponse.class);

        return apiResponse.businesses;

    }

    public static List<Trip> fetchTrips(UserRequest request) throws IOException {
        List<YelpBusiness> yelpBusinesses = Arrays.asList(fetchYelpData(request));
        List<YelpBusiness> expensiveAndClosedRemoved = YelpUtilities
                .removeExpensiveBusinesses((YelpUtilities.removeClosedBusinesses(yelpBusinesses)), request.getMoney());

        @SuppressWarnings("unchecked")
        List<YelpBusiness> sortedByPriceAndDistance = (List<YelpBusiness>) YelpUtilities
                .sortByPriceAndDistance(expensiveAndClosedRemoved);

        List<Trip> trips = new ArrayList<Trip>();

        for (YelpBusiness yBusiness : sortedByPriceAndDistance) {
            // Build the business from YelpBusiness properties

            YelpBusinessLocation businessLocation = yBusiness.getLocation();
            String address = businessLocation.getAddress1() + " " + businessLocation.getCity() + ", "
                    + businessLocation.getZipCode();
            Business business = new Business(yBusiness.getName(), yBusiness.getImage_url(), yBusiness.isClosed(),
                    yBusiness.getPrice(), yBusiness.getPhone(), address, yBusiness.getUrl(), yBusiness.getCoordinates(),
                    yBusiness.getRating());

            // Use our utility functions to calculate the number of minutes and String price range

            int minutes = YelpUtilities.calculateTimeNeeded(yBusiness.getCategories().get(0).getAlias(),
                    business.getCost());
            String cost = YelpUtilities.generateRangeFromDollarSigns(business.getCost());

            // Create the Trip

            Trip trip = new Trip(business, minutes, cost);

            trips.add(trip);

        }

        return trips;

    }
}
package com.kiafarhang.distillr.yelp;

import com.kiafarhang.distillr.yelp.YelpAPIQuery;
import com.kiafarhang.distillr.yelp.YelpBusiness;

import com.kiafarhang.distillr.server.UserRequest;

import com.google.gson.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
        // try {
        //     Scanner fileScanner = new Scanner(apiKeyFile, "UTF-8");
        //     String yelpAPIKey = fileScanner.nextLine();
        // } catch (FileNotFoundException exception) {
        //     System.out.println(
        //             "Error: No API key found; be sure to create 'yelp-key.txt' at the project's root directory");
        // }

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
}
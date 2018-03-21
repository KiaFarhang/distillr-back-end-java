package com.kiafarhang.distillr.yelp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class YelpAPIRequest {
    public static String fetchYelpData() throws IOException {

        // Set up and execute the HTTP request

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://httpbin.org/get");
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

        return content;

    }
}
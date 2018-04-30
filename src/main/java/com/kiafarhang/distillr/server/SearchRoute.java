package com.kiafarhang.distillr.server;

import com.kiafarhang.distillr.yelp.YelpAPIRequest;
import com.kiafarhang.distillr.Trip;
import java.util.List;

import spark.Route;
import spark.Request;
import spark.Response;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class SearchRoute implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        response.header("Content-Type", "application/json");
        Gson gson = new Gson();

        String body = request.body();
        try {
            UserRequest userRequest = gson.fromJson(body, UserRequest.class);
            List<Trip> trips = YelpAPIRequest.fetchTrips(userRequest);

            response.status(200);
            return gson.toJson(trips);
        } catch (Exception e) {
            response.status(400);
            return "400 bad request";
        }

    }
}
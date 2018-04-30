package com.kiafarhang.distillr;

import com.kiafarhang.distillr.yelp.YelpAPIRequest;
import com.kiafarhang.distillr.Location;
import com.kiafarhang.distillr.server.UserRequest;

import com.kiafarhang.distillr.server.Server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.FileNotFoundException;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {

        Server server = new Server();
        server.setUpRoutes();
    }
}

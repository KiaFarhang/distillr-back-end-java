package com.kiafarhang.distillr.server;

import com.kiafarhang.distillr.server.SearchRoute;

import spark.Service;
import spark.Spark.*;
import spark.Request;
import spark.Response;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

public class Server {

    private Service service;

    public Server() {
        this.service = Service.ignite();
        this.service.port(8888);
    }

    public void start() {
        this.service.init();
    }

    public void stop() {
        this.service.stop();
    }

    public void setUpRoutes() {
        this.service.before((request, response) -> {
            response.header("Access-Control-Allow-Headers", "content-type");
            response.header("Access-Control-Allow-Origin", "*");
        });

        this.service.options("/", (Request request, Response response) -> {
            response.header("Access-Control-Request-Method", "POST, OPTIONS");
            response.status(200);
            return "200 OK";
        });

        this.service.post("/", new SearchRoute());

        // this.service.post("/", (request, response) -> {
        //     Gson gson = new Gson();
        //     response.header("Content-Type", "application/json");
        //     // Try to parse request into UserRequest
        //     String body = request.body();
        //     if (body == null || body.length() == 0) {
        //         response.status(400);
        //         return "";
        //     }
        //     try {
        //         UserRequest userRequest = gson.fromJson(body, UserRequest.class);
        //         return userRequest.toString();
        //     } catch (JsonSyntaxException exception) {
        //         System.out.println("\n\n\nTHROWING");
        //         throw exception;
        //     }
        // });

        // this.service.exception(IOException.class, (exception, request, response) -> {
        // });
    }
}
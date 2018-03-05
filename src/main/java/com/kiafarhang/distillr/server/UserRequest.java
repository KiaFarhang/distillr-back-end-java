package com.kiafarhang.distillr.server;

import com.kiafarhang.distillr.Location;
import java.util.Date;
import com.google.gson.*;

public class UserRequest {
    Location location;
    double money;
    String searchTerm;

    public UserRequest(Location location, String startTime, String endTime, float money, String searchTerm) {
        this.location = location;
        this.money = money;
        this.searchTerm = searchTerm;
    }

    public Location getLocation() {
        return this.location;
    }

    public double getMoney() {
        return this.money;
    }

    public String getSearchTerm() {
        return this.searchTerm;
    }

    public static UserRequest parseRequestString(String json) throws JsonSyntaxException {
        Gson gson = new Gson();
        UserRequest request = gson.fromJson(json, UserRequest.class);
        return request;
    }
}
package com.kiafarhang.distillr.server;

import com.kiafarhang.distillr.server.UserRequest;
import com.google.gson.JsonSyntaxException;
import com.kiafarhang.distillr.Location;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserRequestTest {
    @Test
    public void parsesValidString() {
        String validJSON = "{\"location\":{\"latitude\":29.537844800000002,\"longitude\":-98.5219164},\"address\":\"\",\"startTime\":\"16:02\",\"endTime\":\"18:02\",\"money\":\"20\",\"searchTerm\":\"tacos\"}";
        UserRequest request = UserRequest.parseRequestString(validJSON);
        assertEquals(request.getMoney(), 20.0, 0.1);
        assertEquals(request.getSearchTerm(), "tacos");
        Location location = request.getLocation();
        assertEquals(location.getLatitude(), 29.537844800000002, 0.1);
        assertEquals(location.getLongitude(), -98.5219164, 0.1);
    }

    @Test(expected = JsonSyntaxException.class)
    public void throwsOnInvalidString() {
        String invalidJSON = "{\"loasdasdcation\":{\"latsdasditude\"asda:29.537844800000002,\"longitudeasd\":-98.5219164},\"address\":\"\",\"startTime\"asd\"16asd:02\",\"endTime\":\"18:02\",asd\"monasdey\"dasd:\"20\",\"searcsahTeasdasrm\":\"tacos\"}";
        UserRequest request = UserRequest.parseRequestString(invalidJSON);
    }
}
package com.kiafarhang.distillr.yelp;

import com.kiafarhang.distillr.yelp.YelpBusiness;
import com.google.gson.JsonSyntaxException;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;

import static org.junit.Assert.*;

public class YelpBusinessTest {
    @Test
    public void parsesValidString() throws IOException {
        String json = new String(
                Files.readAllBytes(Paths.get("src/test/java/com/kiafarhang/distillr/yelp/yelp-business.json")));
        YelpBusiness business = YelpBusiness.buildYelpBusinessFromJSON(json);
        assertEquals(business.getName(), "Four Barrel Coffee");
    }

    @Test(expected = JsonSyntaxException.class)
    public void throwsOnInvalidString() {
        String invalidJSON = "Jim Jones";
        YelpBusiness business = YelpBusiness.buildYelpBusinessFromJSON(invalidJSON);
    }
}

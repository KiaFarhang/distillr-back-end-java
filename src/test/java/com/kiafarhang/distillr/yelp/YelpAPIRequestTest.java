package com.kiafarhang.distillr.yelp;

import com.kiafarhang.distillr.yelp.YelpAPIRequest;
import com.kiafarhang.distillr.server.UserRequest;
import com.kiafarhang.distillr.Location;
import com.kiafarhang.distillr.yelp.YelpBusiness;

import org.junit.*;
import static org.junit.Assert.*;

import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;

import static org.mockserver.character.Character.NEW_LINE;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.verify.VerificationTimes.exactly;

import org.mockserver.model.Parameter;
import org.mockserver.model.Parameters;

import java.io.File;
import org.apache.commons.io.FileUtils;

public class YelpAPIRequestTest {
    private static ClientAndServer proxy;
    private ClientAndServer mockServer;

    @Before
    public void startMockServer() {
        mockServer = startClientAndServer(8000);
    }

    @After
    public void stopMockServer() {
        mockServer.stop();
    }

    Location mockLocation = new Location(29.537928, -98.521866);

    UserRequest mockRequest = new UserRequest(mockLocation, 25, "hamburgers");

    @Test
    public void parsesValidResponse() throws Exception {

        File mockResponseFile = new File("src/test/java/com/kiafarhang/distillr/yelp/sample-api-response.json");

        String mockResponse = FileUtils.readFileToString(mockResponseFile, "UTF-8");

        Parameter searchParameter = new Parameter("term", mockRequest.getSearchTerm());
        Parameter latitudeParameter = new Parameter("latitude", mockRequest.getLocation().getLatitude().toString());
        Parameter longitudeParameter = new Parameter("longitude", mockRequest.getLocation().getLongitude().toString());

        Parameters parameters = new Parameters(searchParameter, latitudeParameter, longitudeParameter);

        mockServer.when(request().withPath("/v3/businesses/search").withQueryStringParameters(parameters))
                .respond(response().withStatusCode(200).withBody(mockResponse));

        YelpBusiness[] businesses = YelpAPIRequest.fetchYelpData(mockRequest);

        assertEquals(businesses.length, 20, 0);

    }

}
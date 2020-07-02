package gojek.responsecomparator.utility;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;

/**
 * Created by prasantabiswas on 15/09/18.
 */
public class Helper {

    public static Response getResponse(String urlString) throws IOException {
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(urlString);
        return response;
    }
}

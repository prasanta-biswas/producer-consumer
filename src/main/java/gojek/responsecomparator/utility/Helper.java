package gojek.responsecomparator.utility;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;

/**
 * Created by prasantabiswas on 15/09/18.
 */
public class Helper {

    private static final Helper instance = new Helper();

    private Helper() {

    }

    public static Helper getInstance(){
        return instance;
    }

    public Response getResponse(String urlString) throws IOException {
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(urlString);
        return response;
    }
}

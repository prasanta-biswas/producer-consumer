package gojek.responsecomparator.handlers;

import io.restassured.response.Response;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;

import java.util.*;

public class JsonHandler {

    public static boolean compareJson(Response jsonResponse1, Response jsonResponse2) throws JSONException {
        JSONCompareResult result = JSONCompare.compareJSON(jsonResponse1.asString(),jsonResponse2.asString(), JSONCompareMode.STRICT);
        return result.passed();
    }
}

package gojek.responsecomparator;

import gojek.responsecomparator.utility.Helper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by prasantabiswas on 15/09/18.
 */
public class HelperTest {

    @Test
    public void testHTTPSResponse(){
        String url = "https://reqres.in/api/users/1";
        String expected = "{\"data\":{\"id\":1,\"email\":\"george.bluth@reqres.in\",\"first_name\":\"George\",\"last_name\":\"Bluth\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg\"},\"ad\":{\"company\":\"StatusCode Weekly\",\"url\":\"http://statuscode.org/\",\"text\":\"A weekly newsletter focusing on software development, infrastructure, the server, performance, and the stack end of things.\"}}";
        try {
            String actual = Helper.getResponse(url).asString();
            Assert.assertEquals(expected,actual);
        }
        catch (IOException e){
            Assert.assertEquals(expected,e.getMessage());
        }
    }

    @Test
    public void testHTTPResponse(){
        String url = "http://jsonplaceholder.typicode.com/todos/1";
        String expected = "{\n" +
                "  \"userId\": 1,\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"delectus aut autem\",\n" +
                "  \"completed\": false\n" +
                "}";
        try {
            String actual = Helper.getResponse(url).asString();
            Assert.assertEquals(expected,actual);
        }
        catch (IOException e){
            Assert.assertEquals(expected,e.getMessage());
        }
    }
}

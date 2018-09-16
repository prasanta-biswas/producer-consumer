package gojek.responsecomparator.test;

import gojek.responsecomparator.utility.Helper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by prasantabiswas on 15/09/18.
 */
public class HelperTest {

    Helper helper = Helper.getInstance();

    @Test
    public void testHTTPSResponse(){
        String url = "https://reqres.in/api/users/1";
        String expected = "{\"data\":{\"id\":1,\"first_name\":\"George\",\"last_name\":\"Bluth\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg\"}}";
        try {
            String actual = helper.getResponse(url);
            Assert.assertEquals(expected,actual);
        }
        catch (IOException e){
            Assert.assertEquals(expected,e.getMessage());
        }
    }

    @Test
    public void testHTTPResponse(){
        String url = "http://jsonplaceholder.typicode.com/todos/1";
        String expected = "{  \"userId\": 1,  \"id\": 1,  \"title\": \"delectus aut autem\",  \"completed\": false}";
        try {
            String actual = helper.getResponse(url);
            Assert.assertEquals(expected,actual);
        }
        catch (IOException e){
            Assert.assertEquals(expected,e.getMessage());
        }
    }
}

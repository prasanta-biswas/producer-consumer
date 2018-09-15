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
        Helper helper = Helper.getInstance();
        String url = "https://reqres.in/api/users/1";
        String expected = "{\n" +
                "  \"data\": {\n" +
                "    \"id\": 1,\n" +
                "    \"first_name\": \"George\",\n" +
                "    \"last_name\": \"Bluth\",\n" +
                "    \"avatar\": \"https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg\"\n" +
                "  }\n" +
                "}";
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
        Helper helper = Helper.getInstance();
        String url = "http://jsonplaceholder.typicode.com/todos/1";
        String expected = "{\n" +
                "  \"userId\": 1,\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"delectus aut autem\",\n" +
                "  \"completed\": false\n" +
                "}";
        try {
            String actual = helper.getResponse(url);
            Assert.assertEquals(expected,actual);
        }
        catch (IOException e){
            Assert.assertEquals(expected,e.getMessage());
        }
    }

    @Test
    public void testPrettifyResponseWithJSON(){
        Helper helper = Helper.getInstance();
        String expected = "{\n" +
                "  \"userId\": 1,\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"delectus aut autem\",\n" +
                "  \"completed\": false\n" +
                "}";
        String actual = helper.prettifyResponse("{\"userId\": 1,\"id\": 1,\"title\": \"delectus aut autem\",\"completed\": false}");
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testPrettifyResponseWithXML(){
        Helper helper = Helper.getInstance();
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<note>\n" +
                "  <to>Tove</to>\n" +
                "  <from>Jani</from>\n" +
                "  <heading>Reminder</heading>\n" +
                "  <body>Don't forget me this weekend!</body>\n" +
                "</note>";
        String actual = helper.prettifyResponse("<note>\n" +
                "<to>Tove</to>\n" +
                "<from>Jani</from>\n" +
                "<heading>Reminder</heading>\n" +
                "<body>Don't forget me this weekend!</body>\n" +
                "</note>");
        Assert.assertEquals(expected,actual);
    }
}

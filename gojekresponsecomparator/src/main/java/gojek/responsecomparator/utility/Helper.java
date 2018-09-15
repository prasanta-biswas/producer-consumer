package gojek.responsecomparator.utility;

import org.apache.commons.io.IOUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

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

    public String getResponse(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection;
        if(urlString.toLowerCase().contains("https"))
            connection = (HttpsURLConnection) url.openConnection();
        else
            connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "text/plain");
        connection.setRequestProperty("User-Agent", "chrome");
        try{
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }
            return IOUtils.toString(connection.getInputStream());
        } finally {
            connection.disconnect();
        }
    }
}

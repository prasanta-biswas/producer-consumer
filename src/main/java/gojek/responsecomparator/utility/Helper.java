package gojek.responsecomparator.utility;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            String response = "";
            while ((inputLine = in.readLine()) != null)
            {
                response = response + inputLine;
            }
            in.close();
            response = response.replaceAll("\n","").replaceAll("\r","")
                    .replaceAll("\t","").replaceAll("\b","");
            return response.toString();
        } finally {
            connection.disconnect();
        }
    }
}

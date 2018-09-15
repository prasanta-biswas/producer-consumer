package gojek.responsecomparator.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
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
            String response = prettifyResponse(IOUtils.toString(connection.getInputStream()));
            return response;
        } finally {
            connection.disconnect();
        }
    }

    public String prettifyResponse(String response){
        try {
            String result = prettifyJson(response);
            return result;
        }
        catch (Exception e){

        }

        try {
            String result = prettifyXml(response);
            return result;
        }
        catch (Exception e){

        }

        return response;

    }

    public String prettifyJson(String jsonString){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(jsonString);
        String formattedJson = gson.toJson(jsonElement);
        return formattedJson;
    }

    public String prettifyXml(String xml) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.parse(new InputSource(new StringReader(xml)));

        OutputFormat format = new OutputFormat(doc);
        format.setIndenting(true);
        format.setIndent(2);
        format.setOmitXMLDeclaration(false);
        format.setLineWidth(Integer.MAX_VALUE);
        Writer outxml = new StringWriter();
        XMLSerializer serializer = new XMLSerializer(outxml, format);
        serializer.serialize(doc);
        String result = outxml.toString();
        return result.substring(0,result.lastIndexOf("\n"));
    }
}

package gojek.responsecomparator.handlers;

import io.restassured.response.Response;
import org.xml.sax.SAXException;
import org.custommonkey.xmlunit.Diff;
import java.io.IOException;

public class XmlHandler {

    public static boolean compareXml(Response xmlResponse1, Response xmlResponse2) throws IOException, SAXException {
        Diff diff = new Diff(xmlResponse1.asString(), xmlResponse2.asString());
        boolean result = diff.identical();
        return result;
    }
}
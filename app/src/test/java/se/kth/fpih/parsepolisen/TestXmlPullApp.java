package se.kth.fpih.parsepolisen;

import android.net.Uri;

import static org.junit.Assert.*;
import org.junit.Test;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;

/**
 * Created by Fredrik Pihlqvist on 2017-04-09.
 */
public class TestXmlPullApp {

    @Test
    public void main ()
        throws XmlPullParserException, IOException {

        File file = new File("E:\\Development\\AndroidProjekt\\ParsePolisen\\app\\src\\main\\res\\raw\\handelser_rss.xml");
        FileReader fr = new FileReader(file);


        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        xpp.setInput( fr );
        int eventType = xpp.getEventType();

        while (eventType != xpp.END_DOCUMENT) {
            System.out.println(xpp.getName());
            eventType = xpp.next();
        }
    }
}
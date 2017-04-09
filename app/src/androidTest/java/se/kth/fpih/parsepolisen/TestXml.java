package se.kth.fpih.parsepolisen;

import android.net.Uri;

import static org.junit.Assert.*;
import org.junit.Test;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import android.content.res.Resources;
import android.util.Log;

import javax.annotation.Resource;

/**
 * Created by Fredrik Pihlqvist on 2017-04-09.
 */
public class TestXml {

    private static final String TAG = "TEST";

    @Test
    public void main ()
            throws XmlPullParserException, IOException {
        Log.d(TAG, "START OF TEST");

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        xpp.setInput(new StringReader(
                "<rss xmlns:dc=\"http://purl.org/dc/elements/1.1/\" version=\"2.0\">\n"
                + "<channel>\n"
                + "<title>Händelser RSS - Stockholms län</title>\n"
                + "<link>http://polisen.se/</link>\n"
                + "<description>\n"
                + "</description>\n"
                + "<item>\n"
                + "<title>2017-04-07 17:04, Trafikolycka, Södertälje</title>\n"
                + "<link>http://polisen.se/Stockholms_lan/Aktuellt/Handelser/Stockholms-lan/2017-04-07-1704-Trafikolycka-Sodertalje/</link>\n"
                + "<description>Ingen skadad vid olyckan på Ängsvägen.</description>\n"
                + "<guid isPermaLink=\"false\">http://polisen.se/Stockholms_lan/Aktuellt/Handelser/Stockholms-lan/2017-04-07-1704-Trafikolycka-Sodertalje/_Fri,-07-Apr-2017-15:40:19-GMT</guid>\n"
                + "<pubDate>Fri, 07 Apr 2017 15:40:19 GMT</pubDate>\n"
                + "</item>\n"
                + "</channel>\n"
                + "</rss>\n"
        ));
        int eventType = xpp.getEventType();

        String name = null;
        String text = null;
        while (eventType != xpp.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("item")) {
                readItem(xpp, eventType);
            }
            eventType = xpp.next();
        }
        Log.d(TAG, "End document");

        Log.d(TAG, "END OF TEST");
    }

    private void readItem(XmlPullParser xpp, int eventType)
        throws XmlPullParserException, IOException {

        Log.d(TAG, "readItem");
        String title = null;
        String link = null;
        String description = null;
        String pubDate = null;

        int testI = 0;
        eventType = xpp.next();

        while (!(eventType == XmlPullParser.END_TAG && xpp.getName().equals("item"))) {
            Log.d(TAG, "Testindex: " + ++testI);
            if (eventType == XmlPullParser.START_TAG) {
                if (xpp.getName().equals("title")) {
                    eventType = xpp.next();
                    title = xpp.getText();
                } else if (xpp.getName().equals("link")) {
                    eventType = xpp.next();
                    link = xpp.getText();
                } else if (xpp.getName().equals("description")) {
                    eventType = xpp.next();
                    description = xpp.getText();
                } else if (xpp.getName().equals("pubDate")) {
                    eventType = xpp.next();
                    pubDate = xpp.getText();
                }
            }
            eventType = xpp.next();
        }

        Log.d(TAG, "title ="+title+"; link="+link+"; desc="+description+"; pubDate="+pubDate);

    }

}
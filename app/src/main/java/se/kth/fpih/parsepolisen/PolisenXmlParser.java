package se.kth.fpih.parsepolisen;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fredrik Pihlqvist on 2017-04-07.
 *
 * This parses the inputstream from the Swedish Police and stores it into a array
 * made of specific objects.
 *
 */

public class PolisenXmlParser {

    private static final String ns = null;

    public static class Item {
        public final String title;
        public final String link;
        public final String description;
        public final String pubDate;

        private Item(String title, String link, String description, String pubDate) {
            this.title = title;
            this.link = link;
            this.description = description;
            this.pubDate = pubDate;
        }

        public String toString() {
            return title;
        }
    }

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        ArrayList<PolisenXmlParser.Item> items = new ArrayList<>();
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        xpp.setInput(in, null);
        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("item")) {
                items.add(readItem(xpp));
            }
            eventType = xpp.next();
        }
        return items;
    }

    // Reads item from XmlParser and returns and item class
    private Item readItem(XmlPullParser xpp)
            throws XmlPullParserException, IOException {

        String title = null;
        String link = null;
        String description = null;
        String pubDate = null;
        int eventType = xpp.next();

        while (!(eventType == XmlPullParser.END_TAG && xpp.getName().equals("item"))) {
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
        return new Item(title, link, description, pubDate);
    }

}

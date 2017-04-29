package se.kth.fpih.parsepolisen;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


/**
 * Created by Fredrik Pihlqvist on 2017-04-08.
 *
 * This activity takes a RSS feed from a the Swedish Police and returns the
 * diffirent information into an HTML view on the phone.
 *
 */

public class WebFeeder extends Activity {

    public String URL = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Intent intent = getIntent();
        URL = intent.getStringExtra("URL");
        loadPage();
    }

    // Uses AsyncTask to download the XML feed from polisen.se
    public void loadPage() {
        new WebFeeder.DownloadXmlTask().execute(URL);
    }

    private class DownloadXmlTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return loadXmlFromNetwork(urls[0]);
            } catch (IOException e) {
                return "IO ERROR";
            } catch (XmlPullParserException e) {
                return  "XML PULL PARSER ERROR";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            WebView myWebView = (WebView) findViewById(R.id.webviewer);
            myWebView.loadData(result, "text/html; charset=UTF-8", null);
        }
    }

    private String loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;
        // Instantiate the parser
        PolisenXmlParser polisenXmlParser = new PolisenXmlParser();
        List<PolisenXmlParser.Item> items = null;

        try {
            stream = downloadUrl(urlString);
            items = polisenXmlParser.parse(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        // PolisXmlParser returns a List (called "items" of Item objects.
        // Each Item object represents a singel event in the XML feed
        // This section preocess the items list to combine each item with HTML markup.
        // Each entry is displayed in the UI as a link that optionally includes
        // a text summery
        StringBuilder htmlString = new StringBuilder();
        htmlString.append("<body>");
        for (PolisenXmlParser.Item item : items) {
            htmlString.append("<h2>" + item.title + "</h2>");
            htmlString.append("<a href=\"" + item.link + "\">" + "Polisen link</a>");
            htmlString.append("<p>" + item.description + "</p>");
            htmlString.append("<p>" + item.pubDate + "</p>");
        }
        htmlString.append("</body>");
        return htmlString.toString();
    }

    // Given a string representation of a URL, sets up a connection and gets
    // an input steam
    public InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }

}

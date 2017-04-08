package se.kth.fpih.parsepolisen;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.SimpleFormatter;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public final String URL = getString(R.string.polisenRSS);


    // Uses AsyncTask to download the XML feed from polisen.se
    public void loadPage() {
        new DownloadXmlTask().execute(URL);
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
            setContentView(R.layout.activity_main);
            // Displays the HTML string in the UI via a WebView
            WebView myWebView = (WebView) findViewById(R.id.webviewer);
            myWebView.loadData(result, "text/html", null);
        }
    }

    private String loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;
        // Instantiate the parser
        PolisenXmlParser polisenXmlParser = new PolisenXmlParser();
        List<PolisenXmlParser.Item> items = null;
        String title = null;
        String link = null;
        String description = null;
        String pubDate = null;

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
        for (PolisenXmlParser.Item item : items) {
            htmlString.append("<h1>" + item.title + "</h1>");
            htmlString.append("<p>" + item.link + "</p>");
            htmlString.append("<p>" + item.description + "</p");
            htmlString.append("<p>" + item.pubDate + "</p>");
        }
        return htmlString.toString();
    }

    // Given a string representation of a URL, sets up a connection and gets
    // an input steam
    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds*/);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}

package se.kth.fpih.parsepolisen;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    public void onClick(View view) {
        setContentView(R.layout.activity_webview);
        //WebFeeder.loadPage();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}

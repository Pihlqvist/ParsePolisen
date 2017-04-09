package se.kth.fpih.parsepolisen;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    public String NYHETER = null;
    public String HANDELSER = null;
    public String PRESS = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NYHETER = getString(R.string.nyheter);
        HANDELSER = getString(R.string.handelser);
        PRESS = getString(R.string.press);
    }

    public void onClick1(View view) {
        Intent intent = new Intent(this, WebFeeder.class);
        intent.putExtra("URL", NYHETER);
        startActivity(intent);
    }

    public void onClick2(View view) {
        Intent intent = new Intent(this, WebFeeder.class);
        intent.putExtra("URL", HANDELSER);
        startActivity(intent);
    }

    public void onClick3(View view) {
        Intent intent = new Intent(this, WebFeeder.class);
        intent = intent.putExtra("URL", PRESS);
        startActivity(intent);
    }

}

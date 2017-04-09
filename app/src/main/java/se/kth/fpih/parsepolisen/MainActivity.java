package se.kth.fpih.parsepolisen;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    public void onClick(View view) {
        Intent intent = new Intent(this, WebFeeder.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}

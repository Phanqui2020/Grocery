package com.phanqui.grocery.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.phanqui.grocery.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class Splash_Activity extends AppCompatActivity {

    ShimmerTextView tv;
    Shimmer shimmer;
    Thread myThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv = findViewById(R.id.shimmerText);
        shimmer = new Shimmer();
        shimmer.start(tv);


        myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2800);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
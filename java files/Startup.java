package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Startup extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);

        getSupportActionBar();

        new Handler().postDelayed(() -> {
            // Create an Intent to start the Home Activity
            Intent intent = new Intent(Startup.this, MainActivity.class);
            startActivity(intent);

            // Finish the current activity
            finish();
        }, 2000); // Delay time in milliseconds (2000 milliseconds = 2 seconds)

    }

}
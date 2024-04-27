package com.example.motorcyclesync;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        startButton = findViewById(R.id.startButton);

        // Set click listener for start button
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Start the main activity or desired activity
                Intent intent = new Intent(MainActivity.this, MotorcycleSelectionActivity.class);
                startActivity(intent);
            }
        });
    }

    public static class MotorcycleSelectionActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_motorcycle_selection);

            // Additional code for motorcycle selection activity
            // For example, display motorcycle options, user preferences, etc.
        }
    }
}

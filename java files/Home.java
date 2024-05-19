package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.Database.Login;
import com.example.finalproject.Database.Registration;
import com.example.finalproject.fragments.HotPicksFragment;

public class Home extends AppCompatActivity {

    Button loginButton;
    Button registrationButton; // Add registration button reference
    Button hotPicksButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        // Initialize login button
        loginButton = findViewById(R.id.loginButton);

        // Initialize registration button
        registrationButton = findViewById(R.id.registrationButton);

        // Initialize hot picks button
        hotPicksButton = findViewById(R.id.hotPicksButton);

        // Set click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the login activity when the login button is clicked
                Intent intent = new Intent(Home.this, Login.class);
                startActivity(intent);
            }
        });

        // Set click listener for registration button
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the registration activity when the registration button is clicked
                Intent intent = new Intent(Home.this, Registration.class);
                startActivity(intent);
            }
        });

        // Set click listener for hot picks button
        hotPicksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the hot picks activity when the hot picks button is clicked
                Intent intent = new Intent(Home.this, HotPicksFragment.class);
                startActivity(intent);
            }
        });
    }
}

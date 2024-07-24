package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.Database.Registration;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText textEmail;
    private EditText textPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button registrationButton = findViewById(R.id.registrationButton);

        // Set click listener for login button
        buttonLogin.setOnClickListener(v -> {
            String email = textEmail.getText().toString().trim();
            String password = textPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            } else {
                // Sign in user with email and password using Firebase Auth
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                // Proceed to HomeActivity
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish(); // Finish LoginActivity to prevent back navigation
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Set click listener for registration button
        registrationButton.setOnClickListener(v -> {
            // Navigate to Registration activity
            startActivity(new Intent(LoginActivity.this, Registration.class));
        });
    }
}

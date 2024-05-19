package com.example.finalproject.Database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.R;

public class Registration extends AppCompatActivity {

    private static final String TAG = "RegistrationActivity";
    private EditText textEmail;
    private EditText textPassword;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);
        Button buttonRegister = findViewById(R.id.buttonRegister);
        databaseHelper = new DatabaseHelper(this);

        buttonRegister.setOnClickListener(v -> {
            String email = textEmail.getText().toString().trim();
            String password = textPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Registration.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Email or password is empty");
            } else {
                try {
                    Log.d(TAG, "Checking if user exists with email: " + email);
                    if (databaseHelper.checkUser(email)) {
                        Toast.makeText(Registration.this, "User already exists", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "User already exists: " + email);
                    } else {
                        Log.d(TAG, "Registering new user with email: " + email);
                        boolean isInserted = databaseHelper.addUser(email, password);
                        if (isInserted) {
                            Toast.makeText(Registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            setContentView(R.layout.home_main);
                            Log.d(TAG, "Registration successful");
                        } else {
                            Toast.makeText(Registration.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Registration failed");
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error during registration", e);
                    Toast.makeText(Registration.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

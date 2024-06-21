package com.example.finalproject.Database;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.HomeActivity;
import com.example.finalproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    private EditText textEmail;
    private EditText textPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        mAuth = FirebaseAuth.getInstance();

        buttonRegister.setOnClickListener(v -> {
            String email = textEmail.getText().toString().trim();
            String password = textPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Registration.this, "Excited ka teh?", Toast.LENGTH_SHORT).show();
            } else if (!isConnectedToInternet()) {
                Toast.makeText(Registration.this, "No internet connection", Toast.LENGTH_SHORT).show();
            } else {
                // Create user with email and password using Firebase Authentication
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    // Store additional user data if needed
                                    // For example, you can store user's email in Firebase Realtime Database or Firestore
                                    // Here, we'll store the user's email in Realtime Database
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference usersRef = database.getReference("users");
                                    usersRef.child(user.getUid()).setValue(email);

                                    Toast.makeText(Registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    // Proceed to your desired activity
                                    // For example, you can start the HomeActivity
                                    startActivity(new Intent(Registration.this, HomeActivity.class));
                                    finish();
                                } else {
                                    // If registration fails, display a message to the user.
                                    // Extract the specific reason for failure from the exception
                                    Exception exception = task.getException();
                                    if (exception instanceof FirebaseAuthException) {
                                        String errorCode = ((FirebaseAuthException) exception).getErrorCode();
                                        switch (errorCode) {
                                            case "ERROR_INVALID_EMAIL":
                                                Toast.makeText(Registration.this, "email ba yan?", Toast.LENGTH_SHORT).show();
                                                break;
                                            case "ERROR_WEAK_PASSWORD":
                                                Toast.makeText(Registration.this, "come on, man up!", Toast.LENGTH_SHORT).show();
                                                break;
                                            case "ERROR_EMAIL_ALREADY_IN_USE":
                                                Toast.makeText(Registration.this, "julet julet yarn", Toast.LENGTH_SHORT).show();
                                                break;
                                            default:
                                                Toast.makeText(Registration.this, "Registration failed. Please try again later.", Toast.LENGTH_SHORT).show();
                                                break;
                                        }
                                    } else {
                                        Toast.makeText(Registration.this, "Registration failed. Please try again later.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }
}

package com.example.finalproject.Database;

import static com.example.finalproject.R.id.textEmail;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.finalproject.R;

public class Login extends AppCompatActivity {

    private EditText textEmail;
    private EditText textPassword;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        databaseHelper = new DatabaseHelper(this);

        buttonLogin.setOnClickListener(v -> {
            String email = textEmail.getText().toString().trim();
            String password = textPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
            } else {
                boolean userExists = databaseHelper.checkUser(email);
                if (userExists) {
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    // Navigate to the home screen or main activity
                    setContentView(R.layout.home_main);
                } else {
                    Toast.makeText(Login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

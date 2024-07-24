package com.example.finalproject.Database;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.HomeActivity;
import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddMotorcycle extends AppCompatActivity {

    private EditText textMinPrice, textMaxPrice, textBrand, textYear, textCategory, textfuelSystem, textpower,
            textseatHeight, textModel;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_motorcycle);

        textBrand = findViewById(R.id.textBrand);
        textYear = findViewById(R.id.textYear);
        textMinPrice = findViewById(R.id.minPriceTextView);
        textMaxPrice = findViewById(R.id.maxPriceTextView);
        textCategory = findViewById(R.id.textCategory);
        textfuelSystem = findViewById(R.id.textfuelSystem);
        textpower = findViewById(R.id.textpower);
        textseatHeight = findViewById(R.id.textseatHeight);
        textModel = findViewById(R.id.textModel);

        Button addMotorcycleButton = findViewById(R.id.buttonAdd_Motorcycle);

        addMotorcycleButton.setOnClickListener(v -> addData());
    }

    private void addData() {
        String inputBrand = textBrand.getText().toString().trim();
        String inputYear = textYear.getText().toString().trim();
        String inputMinPrice = textMinPrice.getText().toString().trim();
        String inputMaxPrice = textMaxPrice.getText().toString().trim();
        String inputCategory = textCategory.getText().toString().trim();
        String inputFuelSystem = textfuelSystem.getText().toString().trim();
        String inputPower = textpower.getText().toString().trim();
        String inputSeatHeight = textseatHeight.getText().toString().trim();
        String inputModel = textModel.getText().toString().trim();

        if (inputBrand.isEmpty() || inputYear.isEmpty() || inputMinPrice.isEmpty()
                || inputMaxPrice.isEmpty() || inputCategory.isEmpty() || inputFuelSystem.isEmpty()
                || inputPower.isEmpty() || inputSeatHeight.isEmpty() || inputModel.isEmpty()) {
            Toast.makeText(AddMotorcycle.this, "Please fill up all the data", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference motorcyclesRef = database.getReference("motor");

            Motorcycle motorcycle = new Motorcycle(inputBrand, inputYear, inputMinPrice, inputMaxPrice,
                    inputCategory, inputFuelSystem, inputPower, inputSeatHeight, inputModel);

            motorcyclesRef.push().setValue(motorcycle).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(AddMotorcycle.this, "Motorcycle added successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddMotorcycle.this, HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(AddMotorcycle.this, "Failed to add motorcycle", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

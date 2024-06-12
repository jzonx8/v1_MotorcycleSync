package com.example.finalproject.Database;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject.R;


public class MotorcycleDetailsActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motorcycledetailsactivity);
        TextView textViewDetailModel, textViewDetailYear, textViewDetailPrice, textViewDetailCategory;

        textViewDetailModel = findViewById(R.id.textViewDetailModel);
        textViewDetailYear = findViewById(R.id.textViewDetailYear);
        textViewDetailPrice = findViewById(R.id.textViewDetailPrice);
        textViewDetailCategory = findViewById(R.id.textViewDetailCategory);

        Intent intent = getIntent();
        String Model = intent.getStringExtra("MODEL");
        String year = intent.getStringExtra("YEAR");
        String price = intent.getStringExtra("PRICE");
        String category = intent.getStringExtra("CATEGORY");

        textViewDetailModel.setText(Model);
        textViewDetailYear.setText(year);
        textViewDetailPrice.setText(price);
        textViewDetailCategory.setText(category);
    }
}

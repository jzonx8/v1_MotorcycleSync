package com.example.finalproject.Database;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {
    private AutoCompleteTextView autoCompleteBrand;
    private EditText textYear, textMinPrice, textMaxPrice;
    private Spinner spinnerCategory;
    private MotorcycleAdapter motorcycleAdapter;
    private List<Motorcycle> motorcycleInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        autoCompleteBrand = findViewById(R.id.textBrand);
        textYear = findViewById(R.id.textYear);
        textMinPrice = findViewById(R.id.minPriceTextView);
        textMaxPrice = findViewById(R.id.maxPriceTextView);
        spinnerCategory = findViewById(R.id.spinnerCategory);

        ArrayAdapter<CharSequence> brandAdapter = ArrayAdapter.createFromResource(this,
                R.array.brand_array, android.R.layout.simple_dropdown_item_1line);
        autoCompleteBrand.setAdapter(brandAdapter);
        autoCompleteBrand.setThreshold(1);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        RecyclerView recyclerViewMotorcycles = findViewById(R.id.recyclerViewMotorcycles);
        recyclerViewMotorcycles.setLayoutManager(new LinearLayoutManager(this));

        motorcycleInput = new ArrayList<>();
        motorcycleAdapter = new MotorcycleAdapter(motorcycleInput);
        recyclerViewMotorcycles.setAdapter(motorcycleAdapter);

        Button searchButton = findViewById(R.id.buttonSearch);

        searchButton.setOnClickListener(v -> {
            String inputBrand = autoCompleteBrand.getText().toString();
            String inputYear = textYear.getText().toString();
            String inputMinPrice = textMinPrice.getText().toString();
            String inputMaxPrice = textMaxPrice.getText().toString();
            String inputCategory = spinnerCategory.getSelectedItem().toString();

            readData(inputBrand, inputYear, inputMinPrice, inputMaxPrice, inputCategory);
        });
    }

    private void readData(String inputBrand, String inputYear, String inputMinPrice, String inputMaxPrice, String inputCategory) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("1U-N4kiJDOPQnKIZ2WNm_VIVNArKB4ti_J8qtmTmKjgY/motorcycles");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                motorcycleInput.clear();
                int count = 0; // Initialize a counter

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (count >= 10) break; // Stop if we've already added 10 motorcycles

                    Motorcycle motorcycle = snapshot.getValue(Motorcycle.class);
                    if (motorcycle != null) {
                        boolean matches = inputBrand.isEmpty() || (motorcycle.getBrand() != null && motorcycle.getBrand().toLowerCase().contains(inputBrand.toLowerCase()));

                        if (!inputYear.isEmpty() && (motorcycle.getYear() == null || !motorcycle.getYear().equalsIgnoreCase(inputYear))) {
                            matches = false;
                        }

                        if (!inputMinPrice.isEmpty() || !inputMaxPrice.isEmpty()) {
                            try {
                                double minPrice = inputMinPrice.isEmpty() ? Double.MIN_VALUE : Double.parseDouble(inputMinPrice);
                                double maxPrice = inputMaxPrice.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(inputMaxPrice);
                                if (motorcycle.getPrice() == null) {
                                    matches = false;
                                } else {
                                    double motorcyclePrice = Double.parseDouble(motorcycle.getPrice());
                                    if (motorcyclePrice < minPrice || motorcyclePrice > maxPrice) {
                                        matches = false;
                                    }
                                }
                            } catch (NumberFormatException e) {
                                matches = false;
                            }
                        }

                        if (!inputCategory.isEmpty() && (motorcycle.getCategory() == null || !motorcycle.getCategory().toLowerCase().contains(inputCategory.toLowerCase()))) {
                            matches = false;
                        }

                        if (matches) {
                            motorcycleInput.add(motorcycle);
                            count++; // Increment the counter
                        }
                    }
                }

                motorcycleAdapter.notifyDataSetChanged();

                if (motorcycleInput.isEmpty()) {
                    Toast.makeText(Search.this, "No matching motorcycles found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Search.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


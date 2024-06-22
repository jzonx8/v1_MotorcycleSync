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
    private EditText textYear, textMinPrice, textMaxPrice, textGearbox;
    private Spinner spinnerCategory;
    private MotorcycleAdapter motorcycleAdapter;
    private List<Motorcycle> motorcycleInput;
    private int currentItemCount = 0;
    private int batchSize = 10;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        autoCompleteBrand = findViewById(R.id.textBrand);
        textYear = findViewById(R.id.textYear);
        textMinPrice = findViewById(R.id.minPriceTextView);
        textMaxPrice = findViewById(R.id.maxPriceTextView);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        textGearbox = findViewById(R.id.textGearbox); // Initialize editTextGearbox

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
        Button loadMoreButton = findViewById(R.id.loadMoreButton);
        databaseReference = FirebaseDatabase.getInstance()
                .getReference("1U-N4kiJDOPQnKIZ2WNm_VIVNArKB4ti_J8qtmTmKjgY/motorcycles");

        searchButton.setOnClickListener(v -> {
            currentItemCount = 0;  // Reset the item count
            motorcycleInput.clear();
            motorcycleAdapter.notifyDataSetChanged();
            readData();
        });

        loadMoreButton.setOnClickListener(v -> loadMoreData());
    }

    private void readData() {
        String inputBrand = autoCompleteBrand.getText().toString().trim();
        String inputYear = textYear.getText().toString().trim();
        String inputMinPrice = textMinPrice.getText().toString().trim();
        String inputMaxPrice = textMaxPrice.getText().toString().trim();
        String inputCategory = spinnerCategory.getSelectedItem().toString().trim();
        String inputGearbox = textGearbox.getText().toString().trim(); // Retrieve gearbox input

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                motorcycleInput.clear();
                int count = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (count >= batchSize) break;

                    Motorcycle motorcycle = snapshot.getValue(Motorcycle.class);
                    if ((motorcycle != null) && matchesCriteria(motorcycle, inputBrand, inputYear, inputMinPrice, inputMaxPrice, inputCategory, inputGearbox)) {
                        motorcycleInput.add(motorcycle);
                        count++;
                    }
                }

                currentItemCount = count;  // Update the current item count
                motorcycleAdapter.notifyDataSetChanged();

                if (motorcycleInput.isEmpty()) {
                    recommendData();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Search.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMoreData() {
        String inputBrand = autoCompleteBrand.getText().toString().trim();
        String inputYear = textYear.getText().toString().trim();
        String inputMinPrice = textMinPrice.getText().toString().trim();
        String inputMaxPrice = textMaxPrice.getText().toString().trim();
        String inputCategory = spinnerCategory.getSelectedItem().toString().trim();
        String inputGearbox = textGearbox.getText().toString().trim(); // Retrieve gearbox input

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (count >= batchSize) break;

                    Motorcycle motorcycle = snapshot.getValue(Motorcycle.class);
                    if ((motorcycle != null) && matchesCriteria(motorcycle, inputBrand, inputYear, inputMinPrice, inputMaxPrice, inputCategory, inputGearbox)) {
                        motorcycleInput.add(motorcycle);
                        count++;
                    }
                }

                currentItemCount += count;  // Update the current item count
                motorcycleAdapter.notifyDataSetChanged();

                if (count < batchSize) {
                    Toast.makeText(Search.this, "No more motorcycles to load", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Search.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean matchesCriteria(Motorcycle motorcycle, String brand, String year, String minPrice, String maxPrice, String category, String gearbox) {
        boolean matches = true;

        if (!brand.isEmpty()) {
            matches = motorcycle.getBrand() != null && motorcycle.getBrand().toLowerCase().contains(brand.toLowerCase());
        }

        if (matches && !year.isEmpty()) {
            matches = motorcycle.getYear() != null && motorcycle.getYear().equalsIgnoreCase(year);
        }

        if (matches && !minPrice.isEmpty()) {
            try {
                int minPriceValue = Integer.parseInt(motorcycle.getMinPrice());
                matches = Integer.parseInt(minPrice) <= minPriceValue;
            } catch (NumberFormatException e) {
                matches = false;
            }
        }

        if (matches && !maxPrice.isEmpty()) {
            try {
                int maxPriceValue = Integer.parseInt(motorcycle.getMaxPrice());
                matches = Integer.parseInt(maxPrice) >= maxPriceValue;
            } catch (NumberFormatException e) {
                matches = false;
            }
        }

        if (matches && !category.isEmpty() && !category.equalsIgnoreCase("category")) {
            matches = motorcycle.getCategory() != null && motorcycle.getCategory().toLowerCase().contains(category.toLowerCase());
        }

        if (matches && !gearbox.isEmpty() && !gearbox.equalsIgnoreCase("gearbox")) { // Assuming "gearbox" is the default hint
            matches = motorcycle.getGearbox() != null && motorcycle.getGearbox().equalsIgnoreCase(gearbox);
        }

        return matches;
    }

    private boolean closeMatchCriteria(Motorcycle motorcycle, String brand, String year, String minPrice, String maxPrice, String category, String gearbox) {
        boolean matches = true;

        if (!brand.isEmpty()) {
            matches = motorcycle.getBrand() != null && motorcycle.getBrand().toLowerCase().contains(brand.toLowerCase());
        }

        if (matches && !year.isEmpty()) {
            matches = motorcycle.getYear() != null && Integer.parseInt(motorcycle.getYear()) >= (Integer.parseInt(year) - 1) && Integer.parseInt(motorcycle.getYear()) <= (Integer.parseInt(year) + 1);
        }

        if (matches && !minPrice.isEmpty()) {
            try {
                int minPriceValue = Integer.parseInt(motorcycle.getMinPrice());
                matches = (Integer.parseInt(minPrice) - 500) <= minPriceValue && minPriceValue <= (Integer.parseInt(minPrice) + 500);
            } catch (NumberFormatException e) {
                matches = false;
            }
        }

        if (matches && !maxPrice.isEmpty()) {
            try {
                int maxPriceValue = Integer.parseInt(motorcycle.getMaxPrice());
                matches = (Integer.parseInt(maxPrice) - 500) <= maxPriceValue && maxPriceValue <= (Integer.parseInt(maxPrice) + 500);
            } catch (NumberFormatException e) {
                matches = false;
            }
        }

        if (matches && !category.isEmpty() && !category.equalsIgnoreCase("category")) {
            matches = motorcycle.getCategory() != null && motorcycle.getCategory().toLowerCase().contains(category.toLowerCase());
        }

        if (matches && !gearbox.isEmpty() && !gearbox.equalsIgnoreCase("gearbox")) { // Assuming "gearbox" is the default hint
            String motorcycleGearbox = motorcycle.getGearbox();
            if (motorcycleGearbox != null) {
                // Check if the motorcycle's gearbox contains the selected option
                if (motorcycleGearbox.toLowerCase().contains(gearbox.toLowerCase())) {
                    matches = true;
                } else {
                    matches = false;
                }
            } else {
                matches = false;
            }
        }


        return matches;
    }

    private void recommendData() {
        String inputBrand = autoCompleteBrand.getText().toString().trim();
        String inputYear = textYear.getText().toString().trim();
        String inputMinPrice = textMinPrice.getText().toString().trim();
        String inputMaxPrice = textMaxPrice.getText().toString().trim();
        String inputCategory = spinnerCategory.getSelectedItem().toString().trim();
        String inputGearbox = textGearbox.getText().toString().trim();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (count >= batchSize) break;

                    Motorcycle motorcycle = snapshot.getValue(Motorcycle.class);
                    if (motorcycle != null && closeMatchCriteria(motorcycle, inputBrand, inputYear, inputMinPrice, inputMaxPrice, inputCategory, inputGearbox)) {
                        motorcycleInput.add(motorcycle);
                        count++;
                    }
                }

                currentItemCount += count;  // Update the current item count
                motorcycleAdapter.notifyDataSetChanged();

                if (!motorcycleInput.isEmpty()) {
                    Toast.makeText(Search.this, "Based on your input, there are no exact matches.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Search.this, "Please input adjust the details", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Search.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

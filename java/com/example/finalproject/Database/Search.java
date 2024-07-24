package com.example.finalproject.Database;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Search extends AppCompatActivity {
    private EditText textMinPrice, textMaxPrice;
    private Spinner autoCompleteBrand, spinnerCategory, spinnerGearbox, spinnerYear, spinnerSex;
    private MotorcycleAdapter motorcycleAdapter;
    private List<Motorcycle> motorcycleInput;
    private int currentItemCount = 0;
    private final int batchSize = 10;
    private DatabaseReference databaseReference;
    private NumberPicker heightPicker, weightPicker;
    private RecyclerView recyclerViewMotorcycles;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Initialize UI components
        initializeUIComponents();

        // Initialize Firebase reference

        // Set up RecyclerView
        setUpRecyclerView();

        // Set up button click listeners
        setUpButtonListeners();
    }

    private void initializeUIComponents() {
        autoCompleteBrand = findViewById(R.id.textBrand);
        spinnerYear = findViewById(R.id.spinnerYear);
        textMinPrice = findViewById(R.id.minPriceTextView);
        textMaxPrice = findViewById(R.id.maxPriceTextView);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerGearbox = findViewById(R.id.spinnerGearbox);
        spinnerSex = findViewById(R.id.spinnerSex);
        heightPicker = findViewById(R.id.numberPickerHeight);
        weightPicker = findViewById(R.id.numberPickerWeight);
        recyclerViewMotorcycles = findViewById(R.id.recyclerViewMotorcycles);

        setUpNumberPicker(heightPicker, 120, 230);
        setUpNumberPicker(weightPicker, 30, 250);

        setUpSpinner(autoCompleteBrand, R.array.brand_array);
        setUpSpinner(spinnerCategory, R.array.category_array);
        setUpSpinner(spinnerGearbox, R.array.gearbox_array);
        setUpSpinner(spinnerSex, R.array.sex_array);
        setUpSpinner(spinnerYear, R.array.year_array);
    }

    private void setUpNumberPicker(NumberPicker picker, int minValue, int maxValue) {
        String[] values = new String[maxValue - minValue + 2];
        values[0] = "--";
        for (int i = 1; i < values.length; i++) {
            values[i] = String.valueOf(minValue + i - 1);
        }
        picker.setMinValue(0);
        picker.setMaxValue(values.length - 1);
        picker.setDisplayedValues(values);
        picker.setWrapSelectorWheel(true);
    }
    private void setUpSpinners() {
        setUpSpinner(autoCompleteBrand, R.array.brand_array);
        setUpSpinner(spinnerCategory, R.array.category_array);
        setUpSpinner(spinnerGearbox, R.array.gearbox_array);
        setUpSpinner(spinnerSex, R.array.sex_array);
        setUpSpinner(spinnerYear, R.array.year_array);
    }

    private void setUpSpinner(Spinner spinner, int arrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                arrayResId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setUpRecyclerView() {
        motorcycleInput = new ArrayList<>();
        motorcycleAdapter = new MotorcycleAdapter(motorcycleInput);
        recyclerViewMotorcycles.setAdapter(motorcycleAdapter);
        recyclerViewMotorcycles.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpButtonListeners() {
        Button searchButton = findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(v -> {
            currentItemCount = 0;  // Reset the item count
            motorcycleInput.clear();
            motorcycleAdapter.notifyDataSetChanged();
            readData();
        });
    }

    private void readData() {
        String inputBrand = autoCompleteBrand.getSelectedItem().toString().trim();
        String inputYear = String.valueOf(spinnerYear.getSelectedItem());
        String inputCategory = spinnerCategory.getSelectedItem().toString().trim();
        String inputGearbox = spinnerGearbox.getSelectedItem().toString().trim();
        String inputSex = spinnerSex.getSelectedItem().toString().trim();
        String inputMinPrice = textMinPrice.getText().toString().trim();
        String inputMaxPrice = textMaxPrice.getText().toString().trim();

        int inputHeight = heightPicker.getValue();
        int inputWeight = weightPicker.getValue();

        Log.d(TAG, "Read data: Search criteria: brand=" + inputBrand + ", year=" + inputYear + ", category=" + inputCategory + ", gearbox=" + inputGearbox + ", sex=" + inputSex +
                ", minPrice=" + inputMinPrice + ", maxPrice=" + inputMaxPrice + ", height=" + inputHeight + ", weight=" + inputWeight);

        databaseReference = FirebaseDatabase.getInstance().getReference("10H4poBkpXc90ddrrYrjUW2gQt5MhMa9U42naKSXwHtA/motorcycles");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                motorcycleInput.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Motorcycle motorcycle = snapshot.getValue(Motorcycle.class);
                    if (motorcycle != null) {
                        boolean matches = true;

                        if (matches && !inputBrand.isEmpty() && !inputBrand.equalsIgnoreCase("Brand")) {
                            matches = motorcycle.getBrand() != null && motorcycle.getBrand().toLowerCase().contains(inputBrand.toLowerCase());
                            Log.d(TAG, "Brand match: " + matches);
                        }

                        if (matches && !inputYear.isEmpty() && !inputYear.equalsIgnoreCase("Year")) {
                            matches = motorcycle.getYear() != null && motorcycle.getYear().equalsIgnoreCase(inputYear);
                            Log.d(TAG, "Year match: " + matches);
                        }

                        if (matches && !inputMinPrice.isEmpty() && !inputMaxPrice.isEmpty()) {
                            try {
                                int minMotorcyclePrice = Integer.parseInt(motorcycle.getMinPrice());
                                int maxMotorcyclePrice = Integer.parseInt(motorcycle.getMaxPrice());

                                matches = (Integer.parseInt(inputMinPrice) <= minMotorcyclePrice) && (Integer.parseInt(inputMaxPrice) >= maxMotorcyclePrice);
                                Log.d(TAG, "Min and Max Price match: " + matches);
                            } catch (NumberFormatException e) {
                                Log.e(TAG, "Price parsing error: ", e);
                                matches = false;
                            }
                        }

                        if (matches && !inputCategory.isEmpty() && !inputCategory.equalsIgnoreCase("Category")) {
                            matches = motorcycle.getCategory() != null && motorcycle.getCategory().toLowerCase().contains(inputCategory.toLowerCase());
                            Log.d(TAG, "Category match: " + matches);
                        }

                        if (matches && !inputGearbox.isEmpty() && !inputGearbox.equalsIgnoreCase("Gearbox")) {
                            matches = motorcycle.getGearbox() != null && motorcycle.getGearbox().equalsIgnoreCase(inputGearbox);
                            Log.d(TAG, "Gearbox match: " + matches);
                        }

                        if (matches && inputHeight != 0) {
                            matches = motorcycle.getSeatHeight() != null && Math.abs(Integer.parseInt(motorcycle.getSeatHeight()) - inputHeight) < 760;
                            Log.d(TAG, "Height match: " + matches);
                        }

                        if (matches && inputWeight != 0) {
                            String dryWeightStr = motorcycle.getDryWeight();
                            Log.d(TAG, "Dry Weight String: " + dryWeightStr);

                            try {
                                float dryWeight = Float.parseFloat(dryWeightStr);
                                Log.d(TAG, "Dry Weight: " + dryWeight);

                                if (inputWeight < 69) {
                                    matches = dryWeight <= 500;
                                    Log.d(TAG, "inputWeight < 69: " + matches);
                                } else if (inputWeight >= 70 && inputWeight <= 92) {
                                    matches = dryWeight > 500 && dryWeight <= 800;
                                    Log.d(TAG, "70 <= inputWeight <= 92: " + matches);
                                } else {
                                    matches = dryWeight > 800;
                                    Log.d(TAG, "inputWeight >= 93: " + matches);
                                }
                            } catch (NumberFormatException e) {
                                Log.e(TAG, "Invalid dry weight format: " + dryWeightStr, e);
                                matches = false;
                            }
                        } else {
                            Log.d(TAG, "Initial matches is false or inputWeight is 0");
                        }

                        if (matches && !inputSex.isEmpty() && !inputSex.equalsIgnoreCase("Sex")) {
                            if (motorcycle.getDryWeight() != null && !motorcycle.getDryWeight().isEmpty() && motorcycle.getSeatHeight() != null && !motorcycle.getSeatHeight().isEmpty()) {
                                int dryWeight = Integer.parseInt(motorcycle.getDryWeight());
                                int seatHeight = Integer.parseInt(motorcycle.getSeatHeight());

                                if (inputSex.equalsIgnoreCase("Male")) {
                                    matches = dryWeight >= 540 && seatHeight >= 760;
                                    Log.d(TAG, "Sex match: " + matches);

                                }

                                if (inputSex.equalsIgnoreCase("Female")) {
                                    matches = dryWeight < 540 && seatHeight < 760;
                                    Log.d(TAG, "Sex match: " + matches);

                                }
                            }
                        }

                        Log.d(TAG, "Final matches value: " + matches);

                        if (matches) {
                            motorcycleInput.add(motorcycle);
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
                Toast.makeText(Search.this, "Failed to read data from database.", Toast.LENGTH_LONG).show();
            }
        };

        databaseReference.addValueEventListener(valueEventListener);
    }

}
/*
                //displayRandomMotorcycles(dataSnapshot);

    private void displayRandomMotorcycles(DataSnapshot dataSnapshot) {
        List<Motorcycle> allMotorcycles = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Motorcycle motorcycle = snapshot.getValue(Motorcycle.class);
            if (motorcycle != null) {
                allMotorcycles.add(motorcycle);
            }
        }
        Collections.shuffle(allMotorcycles); // Shuffle to display random motorcycles
        motorcycleInput.clear();
        motorcycleInput.addAll(allMotorcycles);
        motorcycleAdapter.notifyDataSetChanged();
    }
*/
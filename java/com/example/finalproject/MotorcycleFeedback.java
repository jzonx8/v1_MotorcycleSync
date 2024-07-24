package com.example.finalproject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.Database.Motorcycle;
import com.example.finalproject.Database.MotorcycleAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MotorcycleFeedback extends AppCompatActivity {
    private Spinner autoCompleteBrand;
    private EditText textComment, editTextModels;
    private RatingBar ratingBar;
    private FirebaseFirestore firestore;
    private DatabaseReference databaseReference;
    private List<Motorcycle> motorcycleInput;
    private MotorcycleAdapter motorcycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motorcycle_feedback);

        databaseReference = FirebaseDatabase.getInstance()
                .getReference("10H4poBkpXc90ddrrYrjUW2gQt5MhMa9U42naKSXwHtA/models");

        firestore = FirebaseFirestore.getInstance();

        autoCompleteBrand = findViewById(R.id.textBrand);
        editTextModels = findViewById(R.id.editTextModels);
        ratingBar = findViewById(R.id.ratingBar);
        textComment = findViewById(R.id.editTextComment);
        Button submitButton = findViewById(R.id.SubmitButton);

        ArrayAdapter<CharSequence> brandAdapter = ArrayAdapter.createFromResource(this,
                R.array.brand_array, android.R.layout.simple_spinner_item);
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autoCompleteBrand.setAdapter(brandAdapter);

        motorcycleInput = new ArrayList<>();
        motorcycleAdapter = new MotorcycleAdapter(motorcycleInput);

        submitButton.setOnClickListener(v -> submitFeedback());
    }

    private void submitFeedback() {
        String inputBrand = autoCompleteBrand.getSelectedItem().toString().trim();
        String inputModel = editTextModels.getText().toString().trim();
        String inputComment = textComment.getText().toString().trim();
        String userRating = String.valueOf(ratingBar.getRating());

        Feedback feedback = new Feedback(inputComment, userRating, inputBrand, inputModel);

        // Construct query to check if model and brand exist in the database
        Query checkDatabase = databaseReference.orderByChild("Model").equalTo(inputModel);

        checkDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    boolean modelExistsUnderBrand = false;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Motorcycle motorcycle = snapshot.getValue(Motorcycle.class);
                        if (motorcycle != null) {
                            Log.d("MotorcycleFeedback", "Retrieved Motorcycle: " + motorcycle.getModel() + " from Brand: " + motorcycle.getBrand());
                            if (motorcycle.getModel().equalsIgnoreCase(inputModel) && motorcycle.getBrand().equalsIgnoreCase(inputBrand)) {
                                modelExistsUnderBrand = true;
                                float dbRating = Float.parseFloat(motorcycle.getRating());
                                updateRating(inputBrand, inputModel, inputComment, userRating, dbRating);
                                break;
                            }
                        }
                    }
                    if (!modelExistsUnderBrand) {
                        Toast.makeText(MotorcycleFeedback.this, "Model not found under the specified brand", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MotorcycleFeedback.this, "Model not found in the database", Toast.LENGTH_SHORT).show();
                }
                motorcycleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MotorcycleFeedback.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRating(String inputBrand, String inputModel, String inputComment, String userRating, float dbRating) {
        float newRating = (Float.parseFloat(userRating) + dbRating) / 2;

        // Construct query to find the correct model path
        Query modelQuery = databaseReference.orderByChild("Model").equalTo(inputModel);
        modelQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Motorcycle motorcycle = snapshot.getValue(Motorcycle.class);
                        if (motorcycle != null && motorcycle.getBrand().equalsIgnoreCase(inputBrand)) {
                            snapshot.getRef().child("Rating").setValue(String.valueOf(newRating));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MotorcycleFeedback.this, "Error updating data", Toast.LENGTH_SHORT).show();
            }
        });

        // Store feedback in Firestore
        Map<String, Object> feedbackData = new HashMap<>();
        feedbackData.put("Brand", inputBrand);
        feedbackData.put("Model", inputModel);
        feedbackData.put("Rating", userRating);
        feedbackData.put("Comment", inputComment);

        firestore.collection("Feedbacks")
                .add(feedbackData)
                .addOnSuccessListener(documentReference -> {
                    Log.d("MotorcycleFeedback", "Feedback stored in Firestore with ID: " + documentReference.getId());
                    Toast.makeText(MotorcycleFeedback.this, "Feedback submitted successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.w("MotorcycleFeedback", "Error adding document", e);
                    Toast.makeText(MotorcycleFeedback.this, "Error submitting feedback", Toast.LENGTH_SHORT).show();
                });

        textComment.setText("");
        ratingBar.setRating(0);
        Log.d("MotorcycleFeedback", "Brand: " + inputBrand);
        Log.d("MotorcycleFeedback", "Model: " + inputModel);
        Log.d("MotorcycleFeedback", "Rating: " + newRating);
        Log.d("MotorcycleFeedback", "Comment: " + inputComment);
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    private static class Feedback {
        private String comment;
        private float rating;
        private String brand;
        private String model;

        public Feedback(String comment, String rating, String brand, String model) {
            this.comment = comment;
            this.rating = Float.parseFloat(rating);
            this.brand = brand;
            this.model = model;
        }

        // Getters and setters for all fields
        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public float getRating() {
            return rating;
        }

        public void setRating(float rating) {
            this.rating = rating;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }
    }
}
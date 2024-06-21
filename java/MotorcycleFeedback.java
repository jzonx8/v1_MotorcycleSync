package com.example.finalproject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class MotorcycleFeedback extends AppCompatActivity {
    private AutoCompleteTextView autoCompleteBrand;
    private EditText textComment, textModel;
    private RatingBar ratingBar;
    private FirebaseFirestore firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motorcycle_feedback);

        firebase = FirebaseFirestore.getInstance();

        autoCompleteBrand = findViewById(R.id.textBrand);
        textModel = findViewById(R.id.textModel);
        ratingBar = findViewById(R.id.ratingBar);
        textComment = findViewById(R.id.editTextComment);
        Button submitButton = findViewById(R.id.SubmitButton);

        ArrayAdapter<CharSequence> brandAdapter = ArrayAdapter.createFromResource(this,
                R.array.brand_array, android.R.layout.simple_dropdown_item_1line);
        autoCompleteBrand.setAdapter(brandAdapter);
        autoCompleteBrand.setThreshold(1);

        submitButton.setOnClickListener(v -> {
            submitFeedback();
        });
    }

    private void submitFeedback() {
        String inputBrand = autoCompleteBrand.getText().toString().trim();
        String inputModel = textModel.getText().toString().trim();
        String comment = textComment.getText().toString().trim();
        int rating = (int) ratingBar.getRating();

        Feedback feedback = new Feedback(comment, rating, inputBrand, inputModel);

        if (comment.isEmpty()) {
            Toast.makeText(MotorcycleFeedback.this, "Please provide a comment", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isConnectedToInternet()) {
            Toast.makeText(MotorcycleFeedback.this, "No internet connection", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            firebase.collection("Feedbacks")
                    .add(feedback)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(MotorcycleFeedback.this, "Thank You for your Feedback", Toast.LENGTH_SHORT).show();
                        Log.d("MotorcycleFeedback", "Feedback submitted successfully");
                        textComment.setText("");
                        ratingBar.setRating(0);
                        Log.d("MotorcycleFeedback", "Brand: " + inputBrand);
                        Log.d("MotorcycleFeedback", "Model: " + inputModel);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(MotorcycleFeedback.this, "Failed to submit feedback", Toast.LENGTH_SHORT).show();
                        Log.e("MotorcycleFeedback", "Error submitting feedback", e);
                    });
        } catch (Exception e) {
            Log.e("MotorcycleFeedback", "Error during feedback submission", e);
            Toast.makeText(MotorcycleFeedback.this, "Error submitting feedback", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    private class Feedback {
        private String comment;
        private int rating;
        private String brand;
        private String model;

        public Feedback(String comment, int rating, String brand, String model) {
            this.comment = comment;
            this.rating = rating;
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

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
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
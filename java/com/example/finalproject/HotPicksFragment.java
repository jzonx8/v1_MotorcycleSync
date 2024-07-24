package com.example.finalproject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Database.Motorcycle;
import com.example.finalproject.Database.MotorcycleAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HotPicksFragment extends AppCompatActivity {

    private List<Motorcycle> motorcycleList;
    private MotorcycleAdapter adapter;
    private int lastIndex = 0;  // Keep track of the last item index fetched
    private static final int PAGE_SIZE = 10;  // Number of items to fetch per page

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.fragment_hot_picks);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize motorcycle list
        motorcycleList = new ArrayList<>();

        // Initialize adapter
        adapter = new MotorcycleAdapter(motorcycleList);
        recyclerView.setAdapter(adapter);

        // Initialize Load More button
        Button loadMoreButton = findViewById(R.id.loadMoreButton);
        loadMoreButton.setOnClickListener(v -> fetchHighestRatedMotorcycles());

        // Fetch initial motorcycle data from Firebase
        fetchHighestRatedMotorcycles();
    }

    private void fetchHighestRatedMotorcycles() {
        // Check for internet connection
        if (!isConnected()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get reference to the "motorcycles" node in Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("10H4poBkpXc90ddrrYrjUW2gQt5MhMa9U42naKSXwHtA/motorcycles");

        // Query to order by rating in descending order and limit to PAGE_SIZE items starting from the last fetched index
        Query query = databaseReference.orderByChild("Rating").limitToLast(lastIndex + PAGE_SIZE);

        // Attach a ValueEventListener to retrieve data
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the existing motorcycle list if this is the initial load
                if (lastIndex == 0) {
                    motorcycleList.clear();
                }

                int count = 0;

                // Iterate through each child node
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Convert each snapshot into a Motorcycle object
                    Motorcycle motorcycle = snapshot.getValue(Motorcycle.class);

                    // Add the motorcycle to the list if it's not null
                    if (motorcycle != null) {
                        if (count >= lastIndex) {
                            motorcycleList.add(motorcycle);
                            // Log the fetched data
                            Log.d("HotPicksFragment", "Motorcycle: " + motorcycle.getBrand() + " " + motorcycle.getModel());
                        }
                        count++;
                    }
                }

                // Sort the motorcycle list by rating in descending order
                motorcycleList.sort((m1, m2) -> {
                    // Parse the rating strings to float and compare
                    float rating1 = Float.parseFloat(m1.getRating());
                    float rating2 = Float.parseFloat(m2.getRating());
                    return Float.compare(rating2, rating1); // Compare in descending order
                });


                // Update the last index fetched
                lastIndex += PAGE_SIZE;

                // Notify the adapter that the data set has changed
                adapter.notifyDataSetChanged();

                // Show a message if no motorcycles were found
                if (motorcycleList.isEmpty()) {
                    Toast.makeText(HotPicksFragment.this, "No motorcycles found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Show an error message if the data retrieval was canceled or failed
                Toast.makeText(HotPicksFragment.this, "Failed to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("HotPicksFragment", "Database error: " + error.getMessage());
            }
        });
    }


    // Method to check internet connection
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}

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

public class LatestFragment extends AppCompatActivity {

    private List<Motorcycle> motorcycleList;
    private MotorcycleAdapter adapter;
    private int lastIndex = 0;  // Keep track of the last item index fetched
    private static final int PAGE_SIZE = 10;  // Number of items to fetch per page

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.fragment_latest);  // Updated layout reference

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
        loadMoreButton.setOnClickListener(v -> fetchMotorcycleData());

        // Fetch initial motorcycle data from Firebase
        fetchMotorcycleData();
    }

    private void fetchMotorcycleData() {
        // Check for internet connection
        if (!isConnected()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get reference to the "motorcycles" node in Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("1U-N4kiJDOPQnKIZ2WNm_VIVNArKB4ti_J8qtmTmKjgY/motorcycles");

        // Query to order by year in descending order and limit to PAGE_SIZE items starting from the last fetched index
        Query query = databaseReference.orderByChild("Year").limitToLast(lastIndex + PAGE_SIZE);

        // Attach a ValueEventListener to retrieve data
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the existing motorcycle list if this is the initial load
                if (lastIndex == 0) {
                    motorcycleList.clear();
                }

                int count = 0;

                // Iterate through each child node in reverse order
                List<Motorcycle> tempList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Motorcycle motorcycle = snapshot.getValue(Motorcycle.class);
                    if (motorcycle != null) {
                        tempList.add(motorcycle);
                    }
                }

                // Sort the temporary list in descending order of the year
                tempList.sort((m1, m2) -> m2.getYear().compareTo(m1.getYear()));

                // Add the sorted items to the motorcycle list
                for (int i = Math.max(0, tempList.size() - lastIndex - PAGE_SIZE); i < tempList.size(); i++) {
                    motorcycleList.add(tempList.get(i));
                }

                // Update the last index fetched
                lastIndex += PAGE_SIZE;

                // Notify the adapter that the data set has changed
                adapter.notifyDataSetChanged();

                // Show a message if no motorcycles were found
                if (motorcycleList.isEmpty()) {
                    Toast.makeText(LatestFragment.this, "No motorcycles found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Show an error message if the data retrieval was canceled or failed
                Toast.makeText(LatestFragment.this, "Failed to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("LatestFragment", "Database error: " + error.getMessage());
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

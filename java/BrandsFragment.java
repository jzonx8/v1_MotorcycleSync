package com.example.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Database.Brand;
import com.example.finalproject.Database.BrandAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BrandsFragment extends AppCompatActivity {

    private List<Brand> brandList;
    private BrandAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_brands);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize brand list
        brandList = new ArrayList<>();

        // Initialize adapter
        adapter = new BrandAdapter(brandList);
        recyclerView.setAdapter(adapter);

        // Fetch brand data from Firebase
        fetchBrandData();
    }

    private void fetchBrandData() {
        // Get reference to the "brands" node in Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("1U-N4kiJDOPQnKIZ2WNm_VIVNArKB4ti_J8qtmTmKjgY/brands");

        // Attach a ValueEventListener to retrieve data
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the existing brand list
                brandList.clear();

                // Limit to 10 brands
                int count = 0;

                // Iterate through each child node
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (count >= 10) {
                        break; // Exit the loop if we have reached the limit of 10 brands
                    }

                    // Convert each snapshot into a Brand object
                    Brand brand = snapshot.getValue(Brand.class);

                    // Add the brand to the list if it's not null
                    if (brand != null) {
                        brandList.add(brand);
                        count++; // Increment the count of added brands
                    }
                }

                // Log the number of brands fetched
                Log.d("BrandsFragment", "Number of brands fetched: " + brandList.size());

                // Notify the adapter that the data set has changed
                adapter.notifyDataSetChanged();

                // Show a message if no brands were found
                if (brandList.isEmpty()) {
                    Toast.makeText(BrandsFragment.this, "No brands found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Show an error message if the data retrieval was canceled or failed
                Toast.makeText(BrandsFragment.this, "Failed to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

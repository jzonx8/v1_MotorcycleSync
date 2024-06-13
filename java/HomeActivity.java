package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Database.Motorcycle;
import com.example.finalproject.Database.MotorcycleAdapter;
import com.example.finalproject.Database.Search;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerViewMotorcycles;
    private MotorcycleAdapter motorcycleAdapter;
    private List<Motorcycle> motorcycleInput;
    private static final int MAX_RESULTS = 10; // Maximum results to fetch

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        // Initialize toolbar and navigation drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Initialize RecyclerView
        recyclerViewMotorcycles = findViewById(R.id.recyclerViewMotorcycles);
        recyclerViewMotorcycles.setLayoutManager(new LinearLayoutManager(this));

        motorcycleInput = new ArrayList<>();
        motorcycleAdapter = new MotorcycleAdapter(motorcycleInput);
        recyclerViewMotorcycles.setAdapter(motorcycleAdapter);

        // Initialize buttons (optional)
        Button hotPicksButton = findViewById(R.id.hotPicksButton);
        Button latestPicksButton = findViewById(R.id.latestPicksButton);
        Button brandsButton = findViewById(R.id.brandsButton);
        Button searchButton = findViewById(R.id.motorcycleSearchBar);

        // Set click listeners (optional)
        hotPicksButton.setOnClickListener(v -> {
            // Navigate to HotPicksFragment activity
            Intent intent = new Intent(HomeActivity.this, HotPicksFragment.class);
            startActivity(intent);
        });

        latestPicksButton.setOnClickListener(v -> {
            // Navigate to HotPicksFragment activity
            Intent intent = new Intent(HomeActivity.this, LatestFragment.class);
            startActivity(intent);
        });

        brandsButton.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, BrandsFragment.class));
        });

        searchButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, Search.class);
            startActivity(intent);
        });

        // Fetch data from Firebase
        fetchDataFromFirebase();
    }

    private void fetchDataFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("1U-N4kiJDOPQnKIZ2WNm_VIVNArKB4ti_J8qtmTmKjgY/motorcycles");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                motorcycleInput.clear();
                int count = 0; // Initialize counter

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (count >= MAX_RESULTS) {
                        break; // Stop fetching more entries if reached the limit
                    }

                    Motorcycle motorcycle = snapshot.getValue(Motorcycle.class);
                    if (motorcycle != null) {
                        motorcycleInput.add(motorcycle);
                        count++; // Increment counter
                    }
                }
                motorcycleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation item selection
        return true;
    }

    @Override
    public void onBackPressed() {
        // Handle back press
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

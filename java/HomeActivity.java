package com.example.finalproject;

import android.annotation.SuppressLint;
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
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerViewMotorcycles;
    private MotorcycleAdapter motorcycleAdapter;
    private List<Motorcycle> motorcycleInput;
    private List<Motorcycle> completeMotorcycleList; // List to store all motorcycles
    private Button loadMoreButton;
    private static final int MAX_RESULTS = 10; // Maximum results to fetch at a time
    private int currentLoadedItems = 0; // Track the current number of loaded items

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
        completeMotorcycleList = new ArrayList<>();
        motorcycleAdapter = new MotorcycleAdapter(motorcycleInput);
        recyclerViewMotorcycles.setAdapter(motorcycleAdapter);

        // Initialize buttons (optional)
        Button hotPicksButton = findViewById(R.id.hotPicksButton);
        Button latestPicksButton = findViewById(R.id.latestPicksButton);
        Button brandsButton = findViewById(R.id.brandsButton);
        Button searchButton = findViewById(R.id.motorcycleSearchBar);
        loadMoreButton = findViewById(R.id.loadMoreButton);

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

        // Load more button click listener
        loadMoreButton.setOnClickListener(v -> loadMoreItems());

        // Fetch data from Firebase
        fetchDataFromFirebase();
    }

    private void fetchDataFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("1U-N4kiJDOPQnKIZ2WNm_VIVNArKB4ti_J8qtmTmKjgY/motorcycles");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                completeMotorcycleList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Motorcycle motorcycle = snapshot.getValue(Motorcycle.class);
                    if (motorcycle != null) {
                        completeMotorcycleList.add(motorcycle);
                    }
                }

                // Shuffle the list to randomize the order
                Collections.shuffle(completeMotorcycleList);

                // Load the first set of items
                loadMoreItems();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void loadMoreItems() {
        int nextLimit = Math.min(currentLoadedItems + MAX_RESULTS, completeMotorcycleList.size());
        for (int i = currentLoadedItems; i < nextLimit; i++) {
            motorcycleInput.add(completeMotorcycleList.get(i));
        }
        currentLoadedItems = nextLimit;

        motorcycleAdapter.notifyDataSetChanged();

        // Hide the Load More button if all items are loaded
        if (currentLoadedItems >= completeMotorcycleList.size()) {
            loadMoreButton.setVisibility(Button.GONE);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.loginButton:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.nav_bookmark:
                startActivity(new Intent(this, BookmarkActivity.class));
                break;
            case R.id.nav_review:
                startActivity(new Intent(this, MotorcycleFeedback.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
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

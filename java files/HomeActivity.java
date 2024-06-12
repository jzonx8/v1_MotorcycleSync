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

import com.example.finalproject.Database.Search;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;

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

        // Initialize buttons
        Button hotPicksButton = findViewById(R.id.hotPicksButton);
        Button latestPicksButton = findViewById(R.id.latestPicksButton);
        Button brandsButton = findViewById(R.id.brandsButton);
        Button searchButton = findViewById(R.id.motorcycleSearchBar);  // Added search button

        // Set click listener for hot picks button
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

        // Set click listener for search button
        searchButton.setOnClickListener(v -> {
            // Navigate to Search activity
            Intent intent = new Intent(HomeActivity.this, Search.class);
            startActivity(intent);
        });
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

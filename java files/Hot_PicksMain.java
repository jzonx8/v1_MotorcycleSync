package com.example.finalproject;

import android.content.Intent;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class Hot_PicksMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hot_picksmain);
    }

    public void Hot_Picks(View v)
    {
        Intent x = new Intent(Hot_PicksMain.this, Hot_Picks.class);
        startActivity(x);
    }

}
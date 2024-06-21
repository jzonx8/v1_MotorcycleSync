package com.example.finalproject.Database;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;

public class MotorcycleDetailsActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motorcycle_details);

        // Retrieve data from intent
        String brand = getIntent().getStringExtra("brand");
        String model = getIntent().getStringExtra("model");
        String year = getIntent().getStringExtra("year");
        String category = getIntent().getStringExtra("category");
        String minPrice = getIntent().getStringExtra("minPrice");
        String maxPrice = getIntent().getStringExtra("maxPrice");
        String rating = getIntent().getStringExtra("rating");
        String displacement = getIntent().getStringExtra("displacement");
        String power = getIntent().getStringExtra("power");
        String torque = getIntent().getStringExtra("torque");
        String engineCylinder = getIntent().getStringExtra("engineCylinder");
        String engineStroke = getIntent().getStringExtra("engineStroke");
        String gearbox = getIntent().getStringExtra("gearbox");
        String bore = getIntent().getStringExtra("bore");
        String stroke = getIntent().getStringExtra("stroke");
        String fuelCapacity = getIntent().getStringExtra("fuelCapacity");
        String fuelSystem = getIntent().getStringExtra("fuelSystem");
        String fuelControl = getIntent().getStringExtra("fuelControl");
        String coolingSystem = getIntent().getStringExtra("coolingSystem");
        String transmissionType = getIntent().getStringExtra("transmission");
        String dryWeight = getIntent().getStringExtra("dryWeight");
        String wheelbase = getIntent().getStringExtra("wheelbase");
        String seatHeight = getIntent().getStringExtra("seatHeight");
        String frontBrakes = getIntent().getStringExtra("frontBrakes");
        String rearBrakes = getIntent().getStringExtra("rearBrakes");
        String frontTire = getIntent().getStringExtra("frontTire");
        String rearTire = getIntent().getStringExtra("rearTire");
        String frontSuspension = getIntent().getStringExtra("frontSuspension");
        String rearSuspension = getIntent().getStringExtra("rearSuspension");
        String colorOptions = getIntent().getStringExtra("colorOptions");

        // Set data to TextViews with prefixed labels and handle null or empty values
        setTextView(R.id.brandTextView, "", brand);
        setTextView(R.id.modelTextView, "", model);
        setTextView(R.id.yearTextView, "Year: ", year);
        setTextView(R.id.categoryTextView, "Category: ", category);
        setTextView(R.id.minPriceTextView, "Price: Php ", minPrice);
        setTextView(R.id.maxPriceTextView, "- Php ", maxPrice);
        setTextView(R.id.ratingTextView, "Rating: ", rating);
        setTextView(R.id.displacementTextView, "Displacement: ", displacement);
        setTextView(R.id.powerTextView, "Power: ", power);
        setTextView(R.id.torqueTextView, "Torque: ", torque);
        setTextView(R.id.engineCylinderTextView, "Engine Cylinder: ", engineCylinder);
        setTextView(R.id.engineStrokeTextView, "Engine Stroke: ", engineStroke);
        setTextView(R.id.gearboxTextView, "Gearbox: ", gearbox);
        setTextView(R.id.boreTextView, "Bore: ", bore);
        setTextView(R.id.strokeTextView, "Stroke: ", stroke);
        setTextView(R.id.fuelCapacityTextView, "Fuel Capacity: ", fuelCapacity);
        setTextView(R.id.fuelSystemTextView, "Fuel System: ", fuelSystem);
        setTextView(R.id.fuelControlTextView, "Fuel Control: ", fuelControl);
        setTextView(R.id.coolingSystemTextView, "Cooling System: ", coolingSystem);
        setTextView(R.id.transmissionTypeTextView, "Transmission Type: ", transmissionType);
        setTextView(R.id.dryWeightTextView, "Dry Weight: ", dryWeight);
        setTextView(R.id.wheelbaseTextView, "Wheelbase: ", wheelbase);
        setTextView(R.id.seatHeightTextView, "Seat Height: ", seatHeight);
        setTextView(R.id.frontBrakesTextView, "Front Brakes: ", frontBrakes);
        setTextView(R.id.rearBrakesTextView, "Rear Brakes: ", rearBrakes);
        setTextView(R.id.frontTireTextView, "Front Tire: ", frontTire);
        setTextView(R.id.rearTireTextView, "Rear Tire: ", rearTire);
        setTextView(R.id.frontSuspensionTextView, "Front Suspension: ", frontSuspension);
        setTextView(R.id.rearSuspensionTextView, "Rear Suspension: ", rearSuspension);
        setTextView(R.id.colorOptionsTextView, "Color Options: ", colorOptions);
    }

    private void setTextView(int textViewId, String prefix, String value) {
        TextView textView = findViewById(textViewId);
        if (value == null || value.equals("null")) {
            textView.setVisibility(View.GONE);
        } else if (value.isEmpty()) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setText(prefix + value);
        }
    }
}

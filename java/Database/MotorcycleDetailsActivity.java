package com.example.finalproject.Database;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.example.finalproject.R;

public class MotorcycleDetailsActivity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motorcycle_details);

        // Retrieve data from intent
        String id = getIntent().getStringExtra("id");
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
        String transmissionType = getIntent().getStringExtra("transmissionType");
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

        // Set data to TextViews with prefixed labels
        ((TextView) findViewById(R.id.idTextView)).setText("ID: " + id);
        ((TextView) findViewById(R.id.brandTextView)).setText(brand);
        ((TextView) findViewById(R.id.modelTextView)).setText(model);
        ((TextView) findViewById(R.id.yearTextView)).setText("Year: " + year);
        ((TextView) findViewById(R.id.categoryTextView)).setText("Category: " + category);
        ((TextView) findViewById(R.id.minPriceTextView)).setText("Price: Php" + minPrice);
        ((TextView) findViewById(R.id.maxPriceTextView)).setText("- Php" + maxPrice);
        ((TextView) findViewById(R.id.ratingTextView)).setText("Rating: " + rating);
        ((TextView) findViewById(R.id.displacementTextView)).setText("Displacement: " + displacement);
        ((TextView) findViewById(R.id.powerTextView)).setText("Power: " + power);
        ((TextView) findViewById(R.id.torqueTextView)).setText("Torque: " + torque);
        ((TextView) findViewById(R.id.engineCylinderTextView)).setText("Engine Cylinder: " + engineCylinder);
        ((TextView) findViewById(R.id.engineStrokeTextView)).setText("Engine Stroke: " + engineStroke);
        ((TextView) findViewById(R.id.gearboxTextView)).setText("Gearbox: " + gearbox);
        ((TextView) findViewById(R.id.boreTextView)).setText("Bore: " + bore);
        ((TextView) findViewById(R.id.strokeTextView)).setText("Stroke: " + stroke);
        ((TextView) findViewById(R.id.fuelCapacityTextView)).setText("Fuel Capacity: " + fuelCapacity);
        ((TextView) findViewById(R.id.fuelSystemTextView)).setText("Fuel System: " + fuelSystem);
        ((TextView) findViewById(R.id.fuelControlTextView)).setText("Fuel Control: " + fuelControl);
        ((TextView) findViewById(R.id.coolingSystemTextView)).setText("Cooling System: " + coolingSystem);
        ((TextView) findViewById(R.id.transmissionTypeTextView)).setText("Transmission Type: " + transmissionType);
        ((TextView) findViewById(R.id.dryWeightTextView)).setText("Dry Weight: " + dryWeight);
        ((TextView) findViewById(R.id.wheelbaseTextView)).setText("Wheelbase: " + wheelbase);
        ((TextView) findViewById(R.id.seatHeightTextView)).setText("Seat Height: " + seatHeight);
        ((TextView) findViewById(R.id.frontBrakesTextView)).setText("Front Brakes: " + frontBrakes);
        ((TextView) findViewById(R.id.rearBrakesTextView)).setText("Rear Brakes: " + rearBrakes);
        ((TextView) findViewById(R.id.frontTireTextView)).setText("Front Tire: " + frontTire);
        ((TextView) findViewById(R.id.rearTireTextView)).setText("Rear Tire: " + rearTire);
        ((TextView) findViewById(R.id.frontSuspensionTextView)).setText("Front Suspension: " + frontSuspension);
        ((TextView) findViewById(R.id.rearSuspensionTextView)).setText("Rear Suspension: " + rearSuspension);
        ((TextView) findViewById(R.id.colorOptionsTextView)).setText("Color Options: " + colorOptions);
    }
}

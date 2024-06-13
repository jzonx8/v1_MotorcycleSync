package com.example.finalproject.Database;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.example.finalproject.R;

public class MotorcycleDetailsActivity extends AppCompatActivity {
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

        // Set data to TextViews
        ((TextView) findViewById(R.id.idTextView)).setText(id);
        ((TextView) findViewById(R.id.brandTextView)).setText(brand);
        ((TextView) findViewById(R.id.modelTextView)).setText(model);
        ((TextView) findViewById(R.id.yearTextView)).setText(year);
        ((TextView) findViewById(R.id.categoryTextView)).setText(category);
        ((TextView) findViewById(R.id.minPriceTextView)).setText(minPrice);
        ((TextView) findViewById(R.id.maxPriceTextView)).setText(maxPrice);
        ((TextView) findViewById(R.id.ratingTextView)).setText(rating);
        ((TextView) findViewById(R.id.displacementTextView)).setText(displacement);
        ((TextView) findViewById(R.id.powerTextView)).setText(power);
        ((TextView) findViewById(R.id.torqueTextView)).setText(torque);
        ((TextView) findViewById(R.id.engineCylinderTextView)).setText(engineCylinder);
        ((TextView) findViewById(R.id.engineStrokeTextView)).setText(engineStroke);
        ((TextView) findViewById(R.id.gearboxTextView)).setText(gearbox);
        ((TextView) findViewById(R.id.boreTextView)).setText(bore);
        ((TextView) findViewById(R.id.strokeTextView)).setText(stroke);
        ((TextView) findViewById(R.id.fuelCapacityTextView)).setText(fuelCapacity);
        ((TextView) findViewById(R.id.fuelSystemTextView)).setText(fuelSystem);
        ((TextView) findViewById(R.id.fuelControlTextView)).setText(fuelControl);
        ((TextView) findViewById(R.id.coolingSystemTextView)).setText(coolingSystem);
        ((TextView) findViewById(R.id.transmissionTypeTextView)).setText(transmissionType);
        ((TextView) findViewById(R.id.dryWeightTextView)).setText(dryWeight);
        ((TextView) findViewById(R.id.wheelbaseTextView)).setText(wheelbase);
        ((TextView) findViewById(R.id.seatHeightTextView)).setText(seatHeight);
        ((TextView) findViewById(R.id.frontBrakesTextView)).setText(frontBrakes);
        ((TextView) findViewById(R.id.rearBrakesTextView)).setText(rearBrakes);
        ((TextView) findViewById(R.id.frontTireTextView)).setText(frontTire);
        ((TextView) findViewById(R.id.rearTireTextView)).setText(rearTire);
        ((TextView) findViewById(R.id.frontSuspensionTextView)).setText(frontSuspension);
        ((TextView) findViewById(R.id.rearSuspensionTextView)).setText(rearSuspension);
        ((TextView) findViewById(R.id.colorOptionsTextView)).setText(colorOptions);
    }
}

package com.example.finalproject.Database;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MotorcycleDetailsActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageView imageView2;
    private FirebaseStorage storage;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motorcycle_details);

        storage = FirebaseStorage.getInstance();
        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);

        String imageUrl = getIntent().getStringExtra("imageUrl");
        String imageUrl2 = getIntent().getStringExtra("imageUrl2");

        loadImage(imageView, imageUrl, "imageView");
        loadImage(imageView2, imageUrl2, "imageView2");

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

        // Set data to TextViews with prefixed labels and hide if null
        setTextView(R.id.brandTextView, "", brand);
        setTextView(R.id.modelTextView, "", model);
        setTextView(R.id.yearTextView, "Year: ", year);
        setTextView(R.id.categoryTextView, "Category: ", category);
        setTextView(R.id.minPriceTextView, "Price: Php", minPrice);
        setTextView(R.id.maxPriceTextView, "- Php", maxPrice);
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
        if (value == null  || value.equals("null") || value.isEmpty()) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setText(prefix + value);
        }
    }
    private void loadImage(ImageView imageView, String imagePath, String logTag) {
        if (imagePath != null && !imagePath.isEmpty()) {
            StorageReference imageRef = storage.getReference().child(imagePath);
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                // Load image using Glide
                Log.d("MotorcycleDetailsActivity", logTag + " URL: " + uri.toString());
                Glide.with(this)
                        .load(uri.toString())
                        .into(imageView);
            }).addOnFailureListener(exception -> {
                // Handle any errors
                Log.e("MotorcycleDetailsActivity", "Error loading " + logTag, exception);
                imageView.setImageResource(R.drawable.loading_image); // Set a default error image
            });
        } else {
            // If imageUrl is null or empty, set the placeholder image directly
            imageView.setImageResource(R.drawable.noavailableimage);
        }
    }
}

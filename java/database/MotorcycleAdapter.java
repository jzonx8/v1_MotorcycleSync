package com.example.finalproject.Database;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.List;

public class MotorcycleAdapter extends RecyclerView.Adapter<MotorcycleAdapter.MotorcycleViewHolder> {
    private List<Motorcycle> motorcycleList;
    private Context context;
    private FirebaseStorage storage;

    public MotorcycleAdapter(List<Motorcycle> motorcycleList) {
        this.motorcycleList = motorcycleList;
        this.storage = FirebaseStorage.getInstance(); // Initialize FirebaseStorage
    }

    @NonNull
    @Override
    public MotorcycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_motorcycle, parent, false);
        return new MotorcycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MotorcycleViewHolder holder, int position) {
        Motorcycle motorcycle = motorcycleList.get(position);
        holder.brandTextView.setText(motorcycle.getBrand());
        holder.modelTextView.setText(motorcycle.getModel());
        holder.yearTextView.setText("Year: " + motorcycle.getYear());
        holder.minPriceTextView.setText("Price: Php" + motorcycle.getMinPrice());
        holder.maxPriceTextView.setText("- Php" + motorcycle.getMaxPrice());
        holder.ratingTextView.setText("Rating: " + motorcycle.getRating());

        loadImage(holder.imageView, motorcycle.getImageUrl(), "imageView");
        loadImage(holder.imageView2, motorcycle.getImageUrl2(), "imageView2");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MotorcycleDetailsActivity.class);
            intent.putExtra("id", motorcycle.getId());
            intent.putExtra("brand", motorcycle.getBrand());
            intent.putExtra("model", motorcycle.getModel());
            intent.putExtra("year", String.valueOf(motorcycle.getYear()));
            intent.putExtra("category", motorcycle.getCategory());
            intent.putExtra("minPrice", String.valueOf(motorcycle.getMinPrice()));
            intent.putExtra("maxPrice", String.valueOf(motorcycle.getMaxPrice()));
            intent.putExtra("rating", String.valueOf(motorcycle.getRating()));
            intent.putExtra("displacement", String.valueOf(motorcycle.getDisplacement()));
            intent.putExtra("power", String.valueOf(motorcycle.getPower()));
            intent.putExtra("torque", String.valueOf(motorcycle.getTorque()));
            intent.putExtra("engineCylinder", String.valueOf(motorcycle.getEngineCylinder()));
            intent.putExtra("engineStroke", String.valueOf(motorcycle.getEngineStroke()));
            intent.putExtra("gearbox", String.valueOf(motorcycle.getGearbox()));
            intent.putExtra("bore", String.valueOf(motorcycle.getBore()));
            intent.putExtra("stroke", String.valueOf(motorcycle.getStroke()));
            intent.putExtra("fuelCapacity", String.valueOf(motorcycle.getFuelCapacity()));
            intent.putExtra("fuelSystem", motorcycle.getFuelSystem());
            intent.putExtra("fuelControl", motorcycle.getFuelControl());
            intent.putExtra("coolingSystem", motorcycle.getCoolingSystem());
            intent.putExtra("transmissionType", motorcycle.getTransmissionType());
            intent.putExtra("dryWeight", String.valueOf(motorcycle.getDryWeight()));
            intent.putExtra("wheelbase", String.valueOf(motorcycle.getWheelbase()));
            intent.putExtra("seatHeight", String.valueOf(motorcycle.getSeatHeight()));
            intent.putExtra("frontBrakes", motorcycle.getFrontBrakes());
            intent.putExtra("rearBrakes", motorcycle.getRearBrakes());
            intent.putExtra("frontTire", motorcycle.getFrontTire());
            intent.putExtra("rearTire", motorcycle.getRearTire());
            intent.putExtra("frontSuspension", motorcycle.getFrontSuspension());
            intent.putExtra("rearSuspension", motorcycle.getRearSuspension());
            intent.putExtra("colorOptions", motorcycle.getColorOptions());
            intent.putExtra("imageUrl", motorcycle.getImageUrl());
            intent.putExtra("imageUrl2", motorcycle.getImageUrl2());
            context.startActivity(intent);
        });
    }
    private void loadImage(ImageView imageView, String imagePath, String logTag) {
        if (imagePath != null && !imagePath.isEmpty()) {
            StorageReference imageRef = storage.getReference().child(imagePath);
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                // Load image using Glide
                Log.d("MotorcycleAdapter", logTag + " URL: " + uri.toString());
                Glide.with(context)
                        .load(uri.toString())
                        .into(imageView);
            }).addOnFailureListener(exception -> {
                // Handle any errors
                Log.e("MotorcycleAdapter", "Error loading " + logTag, exception);
                imageView.setImageResource(R.drawable.loading_image); // Set a default error image
            });
        } else {
            // If imageUrl is null or empty, set the placeholder image directly
            imageView.setImageResource(R.drawable.noavailableimage);
        }
    }

    @Override
    public int getItemCount() {
        return motorcycleList.size();
    }

    public void updateModels(List<Motorcycle> motorcycles) {
        this.motorcycleList = motorcycles;
        notifyDataSetChanged();
    }

    static class MotorcycleViewHolder extends RecyclerView.ViewHolder {
        TextView brandTextView;
        TextView modelTextView;
        TextView yearTextView;
        TextView minPriceTextView;
        TextView maxPriceTextView;
        TextView ratingTextView;
        ImageView imageView;
        ImageView imageView2;

        MotorcycleViewHolder(@NonNull View itemView) {
            super(itemView);
            brandTextView = itemView.findViewById(R.id.brandTextView);
            modelTextView = itemView.findViewById(R.id.modelTextView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            minPriceTextView = itemView.findViewById(R.id.minPriceTextView);
            maxPriceTextView = itemView.findViewById(R.id.maxPriceTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            imageView = itemView.findViewById(R.id.imageView);
            imageView2 = itemView.findViewById(R.id.imageView2);// Initialize ImageView
        }
    }
}

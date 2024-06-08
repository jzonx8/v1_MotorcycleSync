package com.example.finalproject.Database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

import java.text.BreakIterator;
import java.util.List;

public class MotorcycleAdapter extends RecyclerView.Adapter<MotorcycleAdapter.MotorcycleViewHolder> {

    private final List<Motorcycle> motorcycleList;

    public MotorcycleAdapter(List<Motorcycle> motorcycleList) {
        this.motorcycleList = motorcycleList;
    }

    @NonNull
    @Override
    public MotorcycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_motorcycle, parent, false);
        return new MotorcycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MotorcycleViewHolder holder, int position) {
        Motorcycle motorcycle = motorcycleList.get(position);
        holder.brandTextView.setText(motorcycle.getBrand());
        holder.modelTextView.setText(motorcycle.getModel());
        holder.yearTextView.setText(motorcycle.getYear());
        holder.priceTextView.setText(motorcycle.getMinPrice());
        holder.priceTextView.setText(motorcycle.getMaxPrice());
        holder.ratingTextView.setText(motorcycle.getRating());
    }

    @Override
    public int getItemCount() {
        return motorcycleList.size();
    }

    static class MotorcycleViewHolder extends RecyclerView.ViewHolder {

        TextView brandTextView;
        TextView modelTextView;
        TextView yearTextView;
        TextView maxPriceTextView;
        TextView minPriceTextView;
        TextView ratingTextView;

        public MotorcycleViewHolder(@NonNull View itemView) {
            super(itemView);
            brandTextView = itemView.findViewById(R.id.brandTextView);
            modelTextView = itemView.findViewById(R.id.modelTextView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            minPriceTextView = itemView.findViewById(R.id.minPriceTextView);
            maxPriceTextView = itemView.findViewById(R.id.maxPriceTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
        }
    }
}

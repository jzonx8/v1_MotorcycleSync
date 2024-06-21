package com.example.finalproject.Database;

public class Brand {
    private String brands;

    public Brand() {
        // Default constructor required for Firebase
    }

    public Brand(String brands) {
        this.brands = brands;
    }

    public String getBrands() {
        return brands;
    }

    public void setBrands(String brands) {
        this.brands = brands;
    }
}

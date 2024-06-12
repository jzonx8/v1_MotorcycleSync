package com.example.finalproject.Database;

public class SearchOutput {
    private String brand;
    private String year;
    private String minPrice;
    private String maxPrice;
    private String category;

    public SearchOutput() {}

    public SearchOutput(String brand, String year, String minPrice, String maxPrice, String category) {
        this.brand = brand;
        this.year = year;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

package com.example.finalproject.Database;

public class Comment {
    private String comment;
    private String rating;
    private String brand;
    private String model;

    public Comment(String comment, String rating, String brand, String model) {
        this.comment = comment;
        this.rating = rating;
        this.brand = brand;
        this.model = model;
    }

    // Getters and setters for all fields
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) { this.comment = comment; }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getBrand() { return brand; }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}

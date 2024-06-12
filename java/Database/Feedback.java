package com.example.finalproject.Database;

public class Feedback {

    private String id;
    private String comment;
    private String rating;

    public Feedback() {
        // Default constructor required for Firebase
    }

    public Feedback(String comment, String rating) {
        this.comment = comment;
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public String getRating() {
        return rating;
    }
}

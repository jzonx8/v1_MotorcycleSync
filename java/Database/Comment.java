package com.example.finalproject.Database;

public class Comment {

    private String comment;
    private String rating;

    public Comment() {
        // Default constructor required for Firebase
    }

    public Comment(String comment, String rating) {
        this.comment = comment;
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}

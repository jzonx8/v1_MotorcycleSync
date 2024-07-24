package com.example.finalproject.Database;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FeedbackOutput extends AppCompatActivity {

    private static final String TAG = "FeedbackOutput";
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_output);

        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.recyclerViewFeedback);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(this, commentList);
        recyclerView.setAdapter(commentAdapter);

        retrieveComments();
    }

    private void retrieveComments() {
        db.collection("Feedbacks")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        commentList.clear();
                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                            String brand = document.getString("Brand");
                            String model = document.getString("Model");
                            String commentText = document.getString("Comment");
                            String ratingStr = document.getString("Rating");

                            if (brand != null && model != null && commentText != null && ratingStr != null) {
                                try {
                                    Comment newComment = new Comment(commentText, ratingStr, brand, model);
                                    commentList.add(newComment);
                                } catch (NumberFormatException e) {
                                    Log.d(TAG, "Invalid rating value: " + ratingStr);
                                }
                            } else {
                                Log.d(TAG, "Document has incomplete data");
                            }
                        }

                        // Sort the commentList by rating in descending order
                        commentList.sort((c1, c2) -> Float.compare(Float.parseFloat(c2.getRating()), Float.parseFloat(c1.getRating())));

                        // Notify adapter that data set has changed
                        commentAdapter.notifyDataSetChanged();
                    } else {
                        Log.d(TAG, "Error getting feedbacks: ", task.getException());
                    }
                });
    }
}

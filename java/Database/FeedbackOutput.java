package com.example.finalproject.Database;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FeedbackOutput extends AppCompatActivity {

    private static final String TAG = "FeedbackOutput";
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private CommentAdapter adapter;
    private List<Comment> commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_output);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewFeedback);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize List for comments
        commentList = new ArrayList<>();
        adapter = new CommentAdapter(this, commentList);
        recyclerView.setAdapter(adapter);

        // Retrieve comments from Firestore
        retrieveComments();
    }

    private void retrieveComments() {
        db.collection("comments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            commentList.clear();
                            for (DocumentSnapshot document : task.getResult()) {
                                String comment = document.getString("comment");
                                String rating = document.getString("rating");

                                // Create a Comment object
                                Comment newComment = new Comment(comment, rating);
                                commentList.add(newComment);
                            }
                            adapter.notifyDataSetChanged(); // Notify adapter of data change
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}

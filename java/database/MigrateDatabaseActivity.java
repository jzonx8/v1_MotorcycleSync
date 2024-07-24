package com.example.finalproject.Database;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MigrateDatabaseActivity extends AppCompatActivity {

    private static final String TAG = "MigrateDatabaseActivity";

    private FirebaseFirestore firestore;
    private DatabaseReference realtimeDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_migrate_database);

        firestore = FirebaseFirestore.getInstance();
        realtimeDatabase = FirebaseDatabase.getInstance().getReference();

        migrateRealtimeDatabaseToFirestore();
    }

    private void migrateRealtimeDatabaseToFirestore() {
        // Reference to the Realtime Database node you want to migrate
        DatabaseReference sourceRef = realtimeDatabase.child("10H4poBkpXc90ddrrYrjUW2gQt5MhMa9U42naKSXwHtA/motorcycles");

        sourceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Map<String, Object> data = new HashMap<>();
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        data.put(childSnapshot.getKey(), childSnapshot.getValue());
                    }
                    writeToFirestore(snapshot.getKey(), data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error reading from Realtime Database", databaseError.toException());
            }
        });
    }

    private void writeToFirestore(String documentId, Map<String, Object> data) {
        DocumentReference docRef = firestore.collection("motor").document(documentId);

        docRef.set(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        } else {
                            Log.e(TAG, "Error writing document", task.getException());
                        }
                    }
                });
    }
    public void migrateDatabase(View view) {
        migrateRealtimeDatabaseToFirestore();
    }
}

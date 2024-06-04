package com.example.bugpatrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConfirmationActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private DatabaseReference requestDataRef;
    private TextView textRideConfirmation;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        progressBar = findViewById(R.id.progressBar);
        textRideConfirmation = findViewById(R.id.textRideConfirmation);
        cancel = findViewById(R.id.cancel);

        String requestId = getIntent().getStringExtra("requestId");
        String email = getIntent().getStringExtra("email");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete the ride data from the Firebase Realtime Database
                requestDataRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Ride canceled successfully
                        Toast.makeText(ConfirmationActivity.this, "Ride canceled", Toast.LENGTH_SHORT).show();

                        // Navigate back to the LocationActivity
                        Intent intent = new Intent(ConfirmationActivity.this, LocationActivity.class);
                        intent.putExtra("email",email);
                        startActivity(intent);
                        finish(); // Close the ConfirmationActivity
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to cancel the ride
                        Toast.makeText(ConfirmationActivity.this, "Failed to cancel the ride", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        // Initialize Firebase reference
        requestDataRef = FirebaseDatabase.getInstance().getReference("requests").child(requestId);
        requestDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Check if the requested field is true
                Boolean requested = snapshot.child("requested").getValue(Boolean.class);
                if (requested != null && requested) {
                    // If requested is true, hide the progress bar
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    // Handle the confirmation
                    handleConfirmation();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled
                Toast.makeText(ConfirmationActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to handle the confirmation (e.g., navigate to the next activity)
    private void handleConfirmation() {
        // Add your code here to handle the confirmation (e.g., navigate to the next activity)
        textRideConfirmation.setVisibility(TextView.VISIBLE);
    }
}

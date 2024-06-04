package com.example.bugpatrol;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RequestRideActivity extends AppCompatActivity {

    private Spinner spinnerBuggy, spinnerLocation;
    private Button btnSubmitRequest;
    private String[] buggyOptions = {"Buggy 1", "Buggy 2", "Buggy 3"};
    private String[] locationOptions = {"AB3", "AB5","Block-13", "Block-14", "Block-15", "Block-16", "Block-17", "Block-20", "Block-22", "SP"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_ride);

        spinnerBuggy = findViewById(R.id.spinnerBuggy);
        spinnerLocation = findViewById(R.id.spinnerLocation);
        btnSubmitRequest = findViewById(R.id.btnSubmitRequest);

        // Populate buggy spinner
        ArrayAdapter<String> buggyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, buggyOptions);
        buggyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBuggy.setAdapter(buggyAdapter);

        // Populate location spinner
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locationOptions);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(locationAdapter);

        String email = getIntent().getStringExtra("email");
        String name = getIntent().getStringExtra("name");

        btnSubmitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected values from spinners
                String selectedBuggy = spinnerBuggy.getSelectedItem().toString();
                String selectedLocation = spinnerLocation.getSelectedItem().toString();

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                String currentDate = dateFormat.format(calendar.getTime());
                String currentTime = timeFormat.format(calendar.getTime());

                // Build the alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(RequestRideActivity.this);
                builder.setTitle("Confirm Request");
                builder.setMessage("Are you sure you want to submit the request?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Submit the request
                        DatabaseReference requestsRef = FirebaseDatabase.getInstance().getReference("requests");
                        String requestId = requestsRef.push().getKey();
                        RequestData requestData = new RequestData(email, name, selectedBuggy, selectedLocation, false, currentDate, currentTime);
                        requestsRef.child(requestId).setValue(requestData);

                        // Show a success message
                        Toast.makeText(RequestRideActivity.this, "Request submitted successfully", Toast.LENGTH_SHORT).show();

                        // Navigate to the confirmation activity
                        Intent intent = new Intent(RequestRideActivity.this, ConfirmationActivity.class);
                        intent.putExtra("email",email);
                        intent.putExtra("requestId", requestId);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing if the user cancels
                    }
                });

                // Display the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }
}
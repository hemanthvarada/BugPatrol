package com.example.bugpatrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RideDetailsActivity extends AppCompatActivity {
    Button book_ride;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_details);
        book_ride = findViewById(R.id.btn_reqride);
        book_ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RideDetailsActivity.this, "Loading", Toast.LENGTH_SHORT).show();
                Intent ride_req = new Intent(RideDetailsActivity.this, RequestRideActivity.class);
                startActivity(ride_req);
            }
        });
    }
}
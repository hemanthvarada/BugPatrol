package com.example.bugpatrol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllRidesActivity extends AppCompatActivity {

    private ListView listViewRides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_rides);
        listViewRides = findViewById(R.id.listViewRides);

        String email = getIntent().getStringExtra("email");

        DatabaseReference ridesRef = FirebaseDatabase.getInstance().getReference("requests");

        // Query rides associated with the user's email
        Query query = ridesRef.orderByChild("email").equalTo(email);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // List to store ride information
                ArrayList<String> ridesList = new ArrayList<>();

                // Iterate through the dataSnapshot to retrieve ride information
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Get the ride data
                    String rideDetails = "Buggy: " + snapshot.child("selectedBuggy").getValue(String.class)
                            + "\nDate: " + snapshot.child("date").getValue(String.class)
                            + "\nTime: " + snapshot.child("time").getValue(String.class);
                    ridesList.add(rideDetails);
                }

                // Create an ArrayAdapter to populate the ListView
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AllRidesActivity.this,
                        android.R.layout.simple_list_item_1, ridesList);

                // Set the adapter to the ListView
                listViewRides.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AllRidesActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
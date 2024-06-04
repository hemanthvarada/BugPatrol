package com.example.bugpatrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class RidesListActivity extends AppCompatActivity {
    ListView rides;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides_list);
        rides = (ListView) findViewById(R.id.list_rides);
        String[] rideList = new String[]{"Buggy 1", "Buggy 2", "Buggy 3", "Buggy 4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, rideList);
        rides.setAdapter(adapter);
        rides.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(RidesListActivity.this, "Ride Selected: "+(String) rides.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                Intent ride = new Intent(RidesListActivity.this, RideDetailsActivity.class);
                startActivity(ride);
            }
        });
    }
}
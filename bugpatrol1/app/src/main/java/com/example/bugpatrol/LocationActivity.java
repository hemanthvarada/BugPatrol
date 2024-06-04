package com.example.bugpatrol;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Interpolator;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import com.example.bugpatrol.R;


public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private Marker myLocationMarker; // Marker for your location
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private DatabaseReference locationReference;
    private HashMap<String, Marker> markerHashMap = new HashMap<>();
    FloatingActionButton fab;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationReference = FirebaseDatabase.getInstance().getReference("buggy_locations");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
        else
        {
            startLocationUpdates();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng myLocation = new LatLng(0, 0); // Default location
        myLocationMarker = mMap.addMarker(new MarkerOptions().position(myLocation).title("My Location"));

        // Retrieve and display the locations of buggies
        locationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    LocationData locationData = snapshot.getValue(LocationData.class);
                    if (locationData != null) {
                        LatLng location = new LatLng(locationData.getLatitude(), locationData.getLongitude());
                        String buggyName = locationData.getBuggyName();

                        // Check if marker already exists
                        if (markerHashMap.containsKey(buggyName)) {
                            Marker marker = markerHashMap.get(buggyName);
                            animateMarker(marker, location);
                        } else {
                            MarkerOptions markerOptions = new MarkerOptions()
                                    .position(location)
                                    .title(buggyName)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.buggy1));
                            Marker marker = mMap.addMarker(markerOptions);
                            markerHashMap.put(buggyName, marker);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle cancelled event
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        } else {
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000); // Update interval in milliseconds (e.g., every 5 seconds)
        locationRequest.setFastestInterval(3000); // Fastest update interval in milliseconds
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null && locationResult.getLastLocation() != null) {
                    double latitude = locationResult.getLastLocation().getLatitude();
                    double longitude = locationResult.getLastLocation().getLongitude();

                    // Update marker for your location
                    LatLng myLocation = new LatLng(latitude, longitude);
                    if(flag == 0) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
                        flag++;
                    }
                    myLocationMarker.setPosition(myLocation);
                }
            }
        }, null);
    }

    private void animateMarker(final Marker marker, final LatLng toPosition) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 3000; // Animation duration in milliseconds

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = Math.min(1.0f, elapsed / (float) duration);
                double lng = t * toPosition.longitude + (1 - t) * marker.getPosition().longitude;
                double lat = t * toPosition.latitude + (1 - t) * marker.getPosition().latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later (60fps)
                    handler.postDelayed(this, 16);
                }
            }
        });
    }
    public void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_options, popupMenu.getMenu());

        String email = getIntent().getStringExtra("email");
        String name = getIntent().getStringExtra("name");

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String itemName = menuItem.getTitle().toString(); // Get the name of the menu item
                switch (itemName) {
                    case "Request Ride":
                        // Handle request ride option
                        Intent requestRideIntent = new Intent(LocationActivity.this, RequestRideActivity.class);
                        requestRideIntent.putExtra("email",email);
                        requestRideIntent.putExtra("name",name);
                        startActivity(requestRideIntent);
                        return true;
                    case "Show Requests":
                        // Handle show requests option
                        Intent intent = new Intent(LocationActivity.this, AllRidesActivity.class);
                        intent.putExtra("email",email);
                        intent.putExtra("name", name);
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });


        popupMenu.show();
    }

}
package com.example.bugpatrol;


public class LocationData {
    private String buggyName;
    private double latitude;
    private double longitude;

    // Default constructor required for Firebase
    public LocationData() {
    }

    public LocationData(String buggyName, double latitude, double longitude) {
        this.buggyName = buggyName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getBuggyName() {
        return buggyName;
    }

    public void setBuggyName(String buggyName) {
        this.buggyName = buggyName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

package com.example.bugpatrol;

import java.util.Date;

public class RequestData {
    private String email;
    private String name;
    private String selectedBuggy;
    private String selectedLocation;
    private boolean requested;
    private String date;
    private String time;

    public RequestData() {
        // Default constructor required for calls to DataSnapshot.getValue(RequestData.class)
    }

    public RequestData(String email, String name, String selectedBuggy, String selectedLocation, boolean requested, String date, String time) {
        this.email = email;
        this.name = name;
        this.selectedBuggy = selectedBuggy;
        this.selectedLocation = selectedLocation;
        this.requested = requested;
        this.date = date;
        this.time = time;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelectedBuggy() {
        return selectedBuggy;
    }

    public void setSelectedBuggy(String selectedBuggy) {
        this.selectedBuggy = selectedBuggy;
    }

    public String getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(String selectedLocation) {
        this.selectedLocation = selectedLocation;
    }

    public boolean isRequested() {
        return requested;
    }

    public void setRequested(boolean requested) {
        this.requested = requested;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

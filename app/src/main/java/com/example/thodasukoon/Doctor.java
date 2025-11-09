package com.example.thodasukoon;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Doctor {

    // The @SerializedName annotation maps the JSON key to the Java field name.
    // It's a good practice to use it, especially if the names differ.

    @SerializedName("placeId")
    private String placeId;

    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;

    @SerializedName("rating")
    private double rating;

    // The 'location' field in the JSON is an object itself, so we can model it as a nested class.
    @SerializedName("location")
    private LocationDetails location;

    @SerializedName("tags")
    private List<String> tags;

    @SerializedName("directionsUrl")
    private String directionsUrl;

    // --- Getters and Setters for all fields ---

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public LocationDetails getLocation() {
        return location;
    }

    public void setLocation(LocationDetails location) {
        this.location = location;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getDirectionsUrl() {
        return directionsUrl;
    }

    public void setDirectionsUrl(String directionsUrl) {
        this.directionsUrl = directionsUrl;
    }

    /**
     * Nested class to represent the "location" object in the JSON.
     * { "lat": 22.5784464, "lng": 88.4633343 }
     */
    public static class LocationDetails {
        @SerializedName("lat")
        private double lat;

        @SerializedName("lng")
        private double lng;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }
}

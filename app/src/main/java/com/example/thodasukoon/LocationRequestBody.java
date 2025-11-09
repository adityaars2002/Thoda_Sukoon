package com.example.thodasukoon;

public class LocationRequestBody {

    public double lat;
    public double lng;



    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public LocationRequestBody(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}

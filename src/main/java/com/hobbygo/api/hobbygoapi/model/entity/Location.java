package com.hobbygo.api.hobbygoapi.model.entity;

public class Location {

    private Float longitude;
    private Float latitude;

    private Location(){}

    public Location(Float longitude, Float latitude){
        setLongitude(longitude);
        setLatitude(latitude);
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }
}

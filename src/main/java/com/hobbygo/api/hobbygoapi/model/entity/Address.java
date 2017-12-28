package com.hobbygo.api.hobbygoapi.model.entity;

public class Address {
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String zipcode;
    private Location location;

    private Address(){}

    public Address(String street1, String street2, String city, String state,
                   String zipcode, Float longitude, Float latitude){
        buildAddress(street1, street2, city, state, zipcode);
        setLocation(new Location(longitude,latitude));
    }

    private void buildAddress(String street1, String street2, String city, String state,
                              String zipcode) {
        setStreet1(street1);
        setStreet2(street2);
        setCity(city);
        setState(state);
        setZipcode(zipcode);
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

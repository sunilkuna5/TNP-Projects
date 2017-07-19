package com.sample.directions.directionssample.Model;

import android.location.Location;

/**
 * Created by macosx on 19/07/2017 AD.
 */

public class LocationInfo {

    Location location;
    String Address;

    public LocationInfo(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
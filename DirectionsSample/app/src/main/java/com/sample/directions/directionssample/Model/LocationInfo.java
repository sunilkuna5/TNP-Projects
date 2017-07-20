package com.sample.directions.directionssample.Model;

import android.location.Location;

/**
 * Created by macosx on 19/07/2017 AD.
 */

public class LocationInfo {

    Location location;
    String address;

    public LocationInfo(Location location, String s) {
        this.location = location;
        this.address = s;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

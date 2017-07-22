package com.sample.directions.directionssample.Model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by macosx on 19/07/2017 AD.
 */

public class LocationInfo implements Parcelable{

    Location location;
    String address;

    public LocationInfo(Location location, String s) {
        this.location = location;
        this.address = s;
    }

    protected LocationInfo(Parcel in) {
        location = in.readParcelable(Location.class.getClassLoader());
        address = in.readString();
    }

    public static final Creator<LocationInfo> CREATOR = new Creator<LocationInfo>() {
        @Override
        public LocationInfo createFromParcel(Parcel in) {
            return new LocationInfo(in);
        }

        @Override
        public LocationInfo[] newArray(int size) {
            return new LocationInfo[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(location, i);
        parcel.writeString(address);
    }
}

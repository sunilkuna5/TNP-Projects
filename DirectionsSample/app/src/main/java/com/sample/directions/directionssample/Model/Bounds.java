
package com.sample.directions.directionssample.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bounds implements Parcelable
{

    @SerializedName("northeast")
    @Expose
    private Northeast northeast;
    @SerializedName("southwest")
    @Expose
    private Southwest southwest;
    public final static Creator<Bounds> CREATOR = new Creator<Bounds>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Bounds createFromParcel(Parcel in) {
            Bounds instance = new Bounds();
            instance.northeast = ((Northeast) in.readValue((Northeast.class.getClassLoader())));
            instance.southwest = ((Southwest) in.readValue((Southwest.class.getClassLoader())));
            return instance;
        }

        public Bounds[] newArray(int size) {
            return (new Bounds[size]);
        }

    }
    ;

    public Northeast getNortheast() {
        return northeast;
    }

    public void setNortheast(Northeast northeast) {
        this.northeast = northeast;
    }

    public Southwest getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Southwest southwest) {
        this.southwest = southwest;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(northeast);
        dest.writeValue(southwest);
    }

    public int describeContents() {
        return  0;
    }

}

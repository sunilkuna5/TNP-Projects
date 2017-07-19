
package com.sample.directions.directionssample.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Polyline implements Parcelable
{

    @SerializedName("points")
    @Expose
    private String points;
    public final static Creator<Polyline> CREATOR = new Creator<Polyline>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Polyline createFromParcel(Parcel in) {
            Polyline instance = new Polyline();
            instance.points = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Polyline[] newArray(int size) {
            return (new Polyline[size]);
        }

    }
    ;

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(points);
    }

    public int describeContents() {
        return  0;
    }

}


package com.sample.directions.directionssample.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable
{

    @SerializedName("distance")
    @Expose
    private Distance_ distance;
    @SerializedName("duration")
    @Expose
    private Duration_ duration;
    @SerializedName("end_location")
    @Expose
    private EndLocation_ endLocation;
    @SerializedName("html_instructions")
    @Expose
    private String htmlInstructions;
    @SerializedName("polyline")
    @Expose
    private Polyline polyline;
    @SerializedName("start_location")
    @Expose
    private StartLocation_ startLocation;
    @SerializedName("travel_mode")
    @Expose
    private String travelMode;
    @SerializedName("maneuver")
    @Expose
    private String maneuver;
    public final static Creator<Step> CREATOR = new Creator<Step>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Step createFromParcel(Parcel in) {
            Step instance = new Step();
            instance.distance = ((Distance_) in.readValue((Distance_.class.getClassLoader())));
            instance.duration = ((Duration_) in.readValue((Duration_.class.getClassLoader())));
            instance.endLocation = ((EndLocation_) in.readValue((EndLocation_.class.getClassLoader())));
            instance.htmlInstructions = ((String) in.readValue((String.class.getClassLoader())));
            instance.polyline = ((Polyline) in.readValue((Polyline.class.getClassLoader())));
            instance.startLocation = ((StartLocation_) in.readValue((StartLocation_.class.getClassLoader())));
            instance.travelMode = ((String) in.readValue((String.class.getClassLoader())));
            instance.maneuver = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Step[] newArray(int size) {
            return (new Step[size]);
        }

    }
    ;

    public Distance_ getDistance() {
        return distance;
    }

    public void setDistance(Distance_ distance) {
        this.distance = distance;
    }

    public Duration_ getDuration() {
        return duration;
    }

    public void setDuration(Duration_ duration) {
        this.duration = duration;
    }

    public EndLocation_ getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(EndLocation_ endLocation) {
        this.endLocation = endLocation;
    }

    public String getHtmlInstructions() {
        return htmlInstructions;
    }

    public void setHtmlInstructions(String htmlInstructions) {
        this.htmlInstructions = htmlInstructions;
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    public StartLocation_ getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(StartLocation_ startLocation) {
        this.startLocation = startLocation;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public String getManeuver() {
        return maneuver;
    }

    public void setManeuver(String maneuver) {
        this.maneuver = maneuver;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(distance);
        dest.writeValue(duration);
        dest.writeValue(endLocation);
        dest.writeValue(htmlInstructions);
        dest.writeValue(polyline);
        dest.writeValue(startLocation);
        dest.writeValue(travelMode);
        dest.writeValue(maneuver);
    }

    public int describeContents() {
        return  0;
    }

}

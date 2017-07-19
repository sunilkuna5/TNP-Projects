
package com.sample.directions.directionssample.Model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoutesModel implements Parcelable
{

    @SerializedName("geocoded_waypoints")
    @Expose
    private List<GeocodedWaypoint> geocodedWaypoints = null;
    @SerializedName("routes")
    @Expose
    private List<Route> routes = null;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Creator<RoutesModel> CREATOR = new Creator<RoutesModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public RoutesModel createFromParcel(Parcel in) {
            RoutesModel instance = new RoutesModel();
            in.readList(instance.geocodedWaypoints, (GeocodedWaypoint.class.getClassLoader()));
            in.readList(instance.routes, (Route.class.getClassLoader()));
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public RoutesModel[] newArray(int size) {
            return (new RoutesModel[size]);
        }

    }
    ;

    public List<GeocodedWaypoint> getGeocodedWaypoints() {
        return geocodedWaypoints;
    }

    public void setGeocodedWaypoints(List<GeocodedWaypoint> geocodedWaypoints) {
        this.geocodedWaypoints = geocodedWaypoints;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(geocodedWaypoints);
        dest.writeList(routes);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}

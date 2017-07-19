
package com.sample.directions.directionssample.Model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeocodedWaypoint implements Parcelable
{

    @SerializedName("geocoder_status")
    @Expose
    private String geocoderStatus;
    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("types")
    @Expose
    private List<String> types = null;
    public final static Creator<GeocodedWaypoint> CREATOR = new Creator<GeocodedWaypoint>() {


        @SuppressWarnings({
            "unchecked"
        })
        public GeocodedWaypoint createFromParcel(Parcel in) {
            GeocodedWaypoint instance = new GeocodedWaypoint();
            instance.geocoderStatus = ((String) in.readValue((String.class.getClassLoader())));
            instance.placeId = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.types, (String.class.getClassLoader()));
            return instance;
        }

        public GeocodedWaypoint[] newArray(int size) {
            return (new GeocodedWaypoint[size]);
        }

    }
    ;

    public String getGeocoderStatus() {
        return geocoderStatus;
    }

    public void setGeocoderStatus(String geocoderStatus) {
        this.geocoderStatus = geocoderStatus;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(geocoderStatus);
        dest.writeValue(placeId);
        dest.writeList(types);
    }

    public int describeContents() {
        return  0;
    }

}

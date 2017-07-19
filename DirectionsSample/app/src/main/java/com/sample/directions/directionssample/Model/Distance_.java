
package com.sample.directions.directionssample.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Distance_ implements Parcelable
{

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("value")
    @Expose
    private Integer value;
    public final static Creator<Distance_> CREATOR = new Creator<Distance_>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Distance_ createFromParcel(Parcel in) {
            Distance_ instance = new Distance_();
            instance.text = ((String) in.readValue((String.class.getClassLoader())));
            instance.value = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public Distance_[] newArray(int size) {
            return (new Distance_[size]);
        }

    }
    ;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(text);
        dest.writeValue(value);
    }

    public int describeContents() {
        return  0;
    }

}


package com.redmart.catalog.model.productList;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Warehouse implements Parcelable {

    public static final Parcelable.Creator<Warehouse> CREATOR = new Parcelable.Creator<Warehouse>() {
        @Override
        public Warehouse createFromParcel(Parcel source) {
            return new Warehouse(source);
        }

        @Override
        public Warehouse[] newArray(int size) {
            return new Warehouse[size];
        }
    };
    @SerializedName("measure")
    @Expose
    private Measure_ measure;

    public Warehouse() {
    }


    protected Warehouse(Parcel in) {
        this.measure = in.readParcelable(Measure_.class.getClassLoader());
    }

    public Measure_ getMeasure() {
        return measure;
    }

    public void setMeasure(Measure_ measure) {
        this.measure = measure;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.measure, flags);
    }
}

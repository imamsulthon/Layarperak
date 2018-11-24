package com.tothon.layarperak.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.tothon.layarperak.model.Trailer;

import java.util.List;

public class TrailerResponse implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private List<Trailer> results = null;

    protected TrailerResponse(Parcel in) {
        id = in.readInt();
        results = in.createTypedArrayList(Trailer.CREATOR);
    }

    public static final Creator<TrailerResponse> CREATOR = new Creator<TrailerResponse>() {
        @Override
        public TrailerResponse createFromParcel(Parcel in) {
            return new TrailerResponse(in);
        }

        @Override
        public TrailerResponse[] newArray(int size) {
            return new TrailerResponse[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeTypedList(results);
    }
}

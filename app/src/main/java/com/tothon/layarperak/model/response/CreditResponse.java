package com.tothon.layarperak.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.tothon.layarperak.model.Cast;
import com.tothon.layarperak.model.Crew;

import java.util.ArrayList;

public class CreditResponse implements Parcelable {

    @SerializedName("cast")
    private ArrayList<Cast> cast;
    @SerializedName("crew")
    private ArrayList<Crew> crew;

    public CreditResponse() {
    }

    public ArrayList<Cast> getCast() {
        return cast;
    }

    public void setCast(ArrayList<Cast> cast) {
        this.cast = cast;
    }

    public ArrayList<Crew> getCrew() {
        return crew;
    }

    public void setCrew(ArrayList<Crew> crew) {
        this.crew = crew;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.cast);
        dest.writeTypedList(this.crew);
    }

    protected CreditResponse(Parcel in) {
        this.cast = in.createTypedArrayList(Cast.CREATOR);
        this.crew = in.createTypedArrayList(Crew.CREATOR);
    }

    public static final Creator<CreditResponse> CREATOR = new Creator<CreditResponse>() {
        @Override
        public CreditResponse createFromParcel(Parcel in) {
            return new CreditResponse(in);
        }

        @Override
        public CreditResponse[] newArray(int size) {
            return new CreditResponse[size];
        }
    };
}

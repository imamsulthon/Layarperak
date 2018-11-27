package com.tothon.layarperak.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.tothon.layarperak.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class PersonMoviesResponse implements Parcelable {

    @SerializedName("cast")
    private ArrayList<Movie> moviesAsCast = null;

    @SerializedName("crew")
    private ArrayList<Movie> moviesAsCrew = null;

    public PersonMoviesResponse() {
    }

    protected PersonMoviesResponse(Parcel in) {
        moviesAsCast = in.createTypedArrayList(Movie.CREATOR);
        moviesAsCrew = in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Creator<PersonMoviesResponse> CREATOR = new Creator<PersonMoviesResponse>() {
        @Override
        public PersonMoviesResponse createFromParcel(Parcel in) {
            return new PersonMoviesResponse(in);
        }

        @Override
        public PersonMoviesResponse[] newArray(int size) {
            return new PersonMoviesResponse[size];
        }
    };

    public ArrayList<Movie> getMoviesAsCast() {
        return moviesAsCast;
    }

    public void setMoviesAsCast(ArrayList<Movie> moviesAsCast) {
        this.moviesAsCast = moviesAsCast;
    }

    public ArrayList<Movie> getMoviesAsCrew() {
        return moviesAsCrew;
    }

    public void setMoviesAsCrew(ArrayList<Movie> moviesAsCrew) {
        this.moviesAsCrew = moviesAsCrew;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(moviesAsCast);
        dest.writeTypedList(moviesAsCrew);
    }
}

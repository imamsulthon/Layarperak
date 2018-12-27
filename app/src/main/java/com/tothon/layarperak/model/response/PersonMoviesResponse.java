package com.tothon.layarperak.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.tothon.layarperak.model.Movie;
import com.tothon.layarperak.model.MovieGroupByCrew;

import java.util.ArrayList;

public class PersonMoviesResponse implements Parcelable {

    @SerializedName("cast")
    private ArrayList<Movie> moviesAsCast = null;

    @SerializedName("crew")
    private ArrayList<MovieGroupByCrew> moviesGroupAsCrews = null;

    public PersonMoviesResponse() {
    }

    protected PersonMoviesResponse(Parcel in) {
        moviesAsCast = in.createTypedArrayList(Movie.CREATOR);
        moviesGroupAsCrews = in.createTypedArrayList(MovieGroupByCrew.CREATOR);
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

    public ArrayList<MovieGroupByCrew> getMoviesGroupAsCrews() {
        return moviesGroupAsCrews;
    }

    public void setMoviesGroupAsCrews(ArrayList<MovieGroupByCrew> moviesGroupAsCrews) {
        this.moviesGroupAsCrews = moviesGroupAsCrews;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(moviesAsCast);
        dest.writeTypedList(moviesGroupAsCrews);
    }
}

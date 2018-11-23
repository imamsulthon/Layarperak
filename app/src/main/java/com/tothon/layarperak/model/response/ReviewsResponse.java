package com.tothon.layarperak.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.tothon.layarperak.model.Review;

import java.util.ArrayList;

public class ReviewsResponse implements Parcelable {

    @SerializedName("results")
    private ArrayList<Review> results;

    public ReviewsResponse() {
    }

    public ReviewsResponse(ArrayList<Review> results) {
        this.results = results;
    }

    protected ReviewsResponse(Parcel in) {
        results = in.createTypedArrayList(Review.CREATOR);
    }

    public static final Creator<ReviewsResponse> CREATOR = new Creator<ReviewsResponse>() {
        @Override
        public ReviewsResponse createFromParcel(Parcel in) {
            return new ReviewsResponse(in);
        }

        @Override
        public ReviewsResponse[] newArray(int size) {
            return new ReviewsResponse[size];
        }
    };

    public ArrayList<Review> getResults() {
        return results;
    }

    public void setResults(ArrayList<Review> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(results);
    }
}

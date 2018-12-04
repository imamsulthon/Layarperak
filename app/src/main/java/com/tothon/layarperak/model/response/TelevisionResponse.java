package com.tothon.layarperak.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.tothon.layarperak.model.Television;

import java.util.ArrayList;

public class TelevisionResponse implements Parcelable {

    @SerializedName("page") private int page;
    @SerializedName("results") private ArrayList<Television> results;
    @SerializedName("total_results") private int totalResults;
    @SerializedName("total_pages") private int totalPages;

    protected TelevisionResponse(Parcel in) {
        page = in.readInt();
        results = in.createTypedArrayList(Television.CREATOR);
        totalResults = in.readInt();
        totalPages = in.readInt();
    }

    public static final Creator<TelevisionResponse> CREATOR = new Creator<TelevisionResponse>() {
        @Override
        public TelevisionResponse createFromParcel(Parcel in) {
            return new TelevisionResponse(in);
        }

        @Override
        public TelevisionResponse[] newArray(int size) {
            return new TelevisionResponse[size];
        }
    };

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<Television> getResults() {
        return results;
    }

    public void setResults(ArrayList<Television> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeTypedList(results);
        dest.writeInt(totalResults);
        dest.writeInt(totalPages);
    }
}

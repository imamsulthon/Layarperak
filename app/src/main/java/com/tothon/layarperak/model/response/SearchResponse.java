package com.tothon.layarperak.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.tothon.layarperak.model.SearchResult;

import java.util.ArrayList;

public class SearchResponse implements Parcelable {

    @SerializedName("page") private int page;
    @SerializedName("results") private ArrayList<SearchResult> results;
    @SerializedName("total_results") private int totalResults;
    @SerializedName("total_pages") private int totalPages;

    protected SearchResponse(Parcel in) {
        page = in.readInt();
        totalResults = in.readInt();
        totalPages = in.readInt();
    }

    public static final Creator<SearchResponse> CREATOR = new Creator<SearchResponse>() {
        @Override
        public SearchResponse createFromParcel(Parcel in) {
            return new SearchResponse(in);
        }

        @Override
        public SearchResponse[] newArray(int size) {
            return new SearchResponse[size];
        }
    };

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<SearchResult> getResults() {
        return results;
    }

    public void setResults(ArrayList<SearchResult> results) {
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
        dest.writeInt(totalResults);
        dest.writeInt(totalPages);
    }
}

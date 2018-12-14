package com.tothon.layarperak.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.tothon.layarperak.model.Image;

import java.util.ArrayList;

public class TaggedImageResponse implements Parcelable {

    @SerializedName("page") private int page;
    @SerializedName("results") private ArrayList<Image> results;
    @SerializedName("total_results") private int totalResults;
    @SerializedName("total_pages") private int totalPages;

    public TaggedImageResponse() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<Image> getResults() {
        return results;
    }

    public void setResults(ArrayList<Image> results) {
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

    protected TaggedImageResponse(Parcel in) {
        page = in.readInt();
        results = in.createTypedArrayList(Image.CREATOR);
        totalResults = in.readInt();
        totalPages = in.readInt();
    }

    public static final Creator<TaggedImageResponse> CREATOR = new Creator<TaggedImageResponse>() {
        @Override
        public TaggedImageResponse createFromParcel(Parcel in) {
            return new TaggedImageResponse(in);
        }

        @Override
        public TaggedImageResponse[] newArray(int size) {
            return new TaggedImageResponse[size];
        }
    };

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

package com.tothon.layarperak.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.tothon.layarperak.model.Person;

import java.util.ArrayList;

public class PeopleResponse implements Parcelable {

    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private ArrayList<Person> results;

    protected PeopleResponse(Parcel in) {
        page = in.readInt();
        results = in.createTypedArrayList(Person.CREATOR);
    }

    public static final Creator<PeopleResponse> CREATOR = new Creator<PeopleResponse>() {
        @Override
        public PeopleResponse createFromParcel(Parcel in) {
            return new PeopleResponse(in);
        }

        @Override
        public PeopleResponse[] newArray(int size) {
            return new PeopleResponse[size];
        }
    };

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<Person> getResults() {
        return results;
    }

    public void setResults(ArrayList<Person> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeTypedList(results);
    }
}

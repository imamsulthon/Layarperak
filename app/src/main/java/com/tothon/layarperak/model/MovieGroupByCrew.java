package com.tothon.layarperak.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class MovieGroupByCrew extends Movie {

    @SerializedName("job")
    private String job;
    @SerializedName("department")
    private String department;

    public MovieGroupByCrew() {
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.job);
        dest.writeString(this.department);
    }

    private MovieGroupByCrew(Parcel in) {
        super(in);
        this.job = in.readString();
        this.department = in.readString();
    }

    public static final Creator<MovieGroupByCrew> CREATOR = new Creator<MovieGroupByCrew>() {
        @Override
        public MovieGroupByCrew createFromParcel(Parcel source) {
            return new MovieGroupByCrew(source);
        }

        @Override
        public MovieGroupByCrew[] newArray(int size) {
            return new MovieGroupByCrew[size];
        }
    };
}

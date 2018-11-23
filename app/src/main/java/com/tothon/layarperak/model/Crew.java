package com.tothon.layarperak.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class Crew extends Person {

    @SerializedName("job")
    private String job;
    @SerializedName("department")
    private String department;

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
        dest.writeString(this.job);
        dest.writeString(this.department);
    }

    public Crew() {
    }

    protected Crew(Parcel in) {
        this.job = in.readString();
        this.department = in.readString();
    }

    public static final Creator<Crew> CREATOR = new Creator<Crew>() {
        @Override
        public Crew createFromParcel(Parcel source) {
            return new Crew(source);
        }

        @Override
        public Crew[] newArray(int size) {
            return new Crew[size];
        }
    };
}
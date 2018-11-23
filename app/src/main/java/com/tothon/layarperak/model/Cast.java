package com.tothon.layarperak.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class Cast extends Person {

    @SerializedName("character")
    private String character;

    @SerializedName("order")
    private int order;

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.character);
        dest.writeInt(this.order);
    }

    private Cast(Parcel in) {
        super(in);
        this.character = in.readString();
        this.order = in.readInt();
    }

    public static final Creator<Cast> CREATOR = new Creator<Cast>() {
        @Override
        public Cast createFromParcel(Parcel source) {
            return new Cast(source);
        }

        @Override
        public Cast[] newArray(int size) {
            return new Cast[size];
        }
    };

}

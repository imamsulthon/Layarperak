package com.tothon.layarperak.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tothon.layarperak.model.Backdrop;
import com.tothon.layarperak.model.Poster;

import java.util.List;

public class ImagesResponse implements Parcelable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("backdrops")
    private List<Backdrop> backdrops = null;
    @SerializedName("posters")
    private List<Poster> posters = null;

    protected ImagesResponse(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        posters = in.createTypedArrayList(Poster.CREATOR);
    }

    public static final Creator<ImagesResponse> CREATOR = new Creator<ImagesResponse>() {
        @Override
        public ImagesResponse createFromParcel(Parcel in) {
            return new ImagesResponse(in);
        }

        @Override
        public ImagesResponse[] newArray(int size) {
            return new ImagesResponse[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Backdrop> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Backdrop> backdrops) {
        this.backdrops = backdrops;
    }

    public List<Poster> getPosters() {
        return posters;
    }

    public void setPosters(List<Poster> posters) {
        this.posters = posters;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeTypedList(posters);
    }
}

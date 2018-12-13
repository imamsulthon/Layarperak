package com.tothon.layarperak.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.tothon.layarperak.model.Image;

import java.util.List;

public class ImagesResponse implements Parcelable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("backdrops")
    private List<Image> backdrops = null;
    @SerializedName("posters")
    private List<Image> posters = null;
    @SerializedName("profiles")
    private List<Image> profiles = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Image> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Image> backdrops) {
        this.backdrops = backdrops;
    }

    public List<Image> getPosters() {
        return posters;
    }

    public void setPosters(List<Image> posters) {
        this.posters = posters;
    }

    public List<Image> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Image> profiles) {
        this.profiles = profiles;
    }

    protected ImagesResponse(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        backdrops = in.createTypedArrayList(Image.CREATOR);
        posters = in.createTypedArrayList(Image.CREATOR);
        profiles = in.createTypedArrayList(Image.CREATOR);
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
        dest.writeTypedList(backdrops);
        dest.writeTypedList(profiles);
    }
}

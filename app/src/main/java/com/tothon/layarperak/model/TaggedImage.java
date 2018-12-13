package com.tothon.layarperak.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class TaggedImage extends Image {

    @SerializedName("media_type") private String mediaType;
    @SerializedName("media") private Media media;

    public TaggedImage() {
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }
}
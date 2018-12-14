package com.tothon.layarperak.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class TaggedImage extends Image {

    @SerializedName("media_type") private String mediaType;
    @SerializedName("media") private Media media;

    public TaggedImage() {
    }

    protected TaggedImage(Parcel in) {
        super(in);
        mediaType = in.readString();
        media = in.readParcelable(Media.class.getClassLoader());
    }

    public static final Creator<TaggedImage> CREATOR = new Creator<TaggedImage>() {
        @Override
        public TaggedImage createFromParcel(Parcel in) {
            return new TaggedImage(in);
        }

        @Override
        public TaggedImage[] newArray(int size) {
            return new TaggedImage[size];
        }
    };

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
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mediaType);
        dest.writeParcelable(media, flags);
    }
}
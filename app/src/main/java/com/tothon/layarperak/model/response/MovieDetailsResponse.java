package com.tothon.layarperak.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.tothon.layarperak.model.Genre;

import java.util.List;

public class MovieDetailsResponse implements Parcelable {

    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("imdb_id")
    private String imdbId;
    @SerializedName("video")
    private boolean video;
    @SerializedName("title")
    private String title;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("revenue")
    private int revenue;
    @SerializedName("genres")
    private List<Genre> genres;
    @SerializedName("popularity")
    private double popularity;
    @SerializedName("id")
    private int id;
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("budget")
    private int budget;
    @SerializedName("overview")
    private String overview;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("runtime")
    private int runtime;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("tagline")
    private String tagline;
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("homepage")
    private String homepage;
    @SerializedName("status")
    private String status;

    protected MovieDetailsResponse(Parcel in) {
        originalLanguage = in.readString();
        imdbId = in.readString();
        video = in.readByte() != 0;
        title = in.readString();
        backdropPath = in.readString();
        revenue = in.readInt();
        genres = in.createTypedArrayList(Genre.CREATOR);
        popularity = in.readDouble();
        id = in.readInt();
        voteCount = in.readInt();
        budget = in.readInt();
        overview = in.readString();
        originalTitle = in.readString();
        runtime = in.readInt();
        posterPath = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readDouble();
        tagline = in.readString();
        adult = in.readByte() != 0;
        homepage = in.readString();
        status = in.readString();
    }

    public static final Creator<MovieDetailsResponse> CREATOR = new Creator<MovieDetailsResponse>() {
        @Override
        public MovieDetailsResponse createFromParcel(Parcel in) {
            return new MovieDetailsResponse(in);
        }

        @Override
        public MovieDetailsResponse[] newArray(int size) {
            return new MovieDetailsResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(originalLanguage);
        dest.writeString(imdbId);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeString(title);
        dest.writeString(backdropPath);
        dest.writeInt(revenue);
        dest.writeTypedList(genres);
        dest.writeDouble(popularity);
        dest.writeInt(id);
        dest.writeInt(voteCount);
        dest.writeInt(budget);
        dest.writeString(overview);
        dest.writeString(originalTitle);
        dest.writeInt(runtime);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
        dest.writeDouble(voteAverage);
        dest.writeString(tagline);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(homepage);
        dest.writeString(status);
    }
}

package com.tothon.layarperak.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.annotations.PrimaryKey;

public class Television implements Parcelable {

    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("imdb_id")
    private String imdbId;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("name")
    private String title;
    @SerializedName("original_name")
    private String originalTitle;
    @SerializedName("first_air_date")
    private String firstAirDate;
    @SerializedName("last_air_date")
    private String lastAirDate;
    @SerializedName("homepage")
    private String homepage;
    @SerializedName("tagline")
    private String tagline;
    @SerializedName("number_of_episodes")
    @Expose
    private Integer numberOfEpisodes;
    @SerializedName("number_of_seasons")
    @Expose
    private Integer numberOfSeasons;
    @SerializedName("overview")
    private String overview;
    @SerializedName("status")
    private String status;
    @SerializedName("genres")
    private List<Genre> genres;
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("vote_average")
    private Double rating;
    @SerializedName("backdrop_path")
    private String backdropPath;
    private byte[] posterBytes;

    public Television() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public Integer getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(Integer numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public Integer getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(Integer numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public byte[] getPosterBytes() {
        return posterBytes;
    }

    public void setPosterBytes(byte[] posterBytes) {
        this.posterBytes = posterBytes;
    }

    protected Television(Parcel in) {
        id = in.readInt();
        imdbId = in.readString();
        posterPath = in.readString();
        title = in.readString();
        originalTitle = in.readString();
        firstAirDate = in.readString();
        lastAirDate = in.readString();
        homepage = in.readString();
        tagline = in.readString();
        overview = in.readString();
        status = in.readString();
        genres = in.createTypedArrayList(Genre.CREATOR);
        voteCount = in.readInt();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readDouble();
        }
        backdropPath = in.readString();
        posterBytes = in.createByteArray();
    }

    public static final Creator<Television> CREATOR = new Creator<Television>() {
        @Override
        public Television createFromParcel(Parcel in) {
            return new Television(in);
        }

        @Override
        public Television[] newArray(int size) {
            return new Television[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(imdbId);
        dest.writeString(posterPath);
        dest.writeString(title);
        dest.writeString(originalTitle);
        dest.writeString(firstAirDate);
        dest.writeString(lastAirDate);
        dest.writeString(homepage);
        dest.writeString(tagline);
        dest.writeString(overview);
        dest.writeString(status);
        dest.writeTypedList(genres);
        dest.writeInt(voteCount);
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rating);
        }
        dest.writeString(backdropPath);
        dest.writeByteArray(posterBytes);
    }
}

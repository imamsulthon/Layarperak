package com.tothon.layarperak.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchResult {

    @SerializedName("id") private int id;
    @SerializedName("media_type") private String mediaType;

    @SerializedName("title") private String title;
    @SerializedName("name") private String name;

    @SerializedName("release_date") private String date;
    @SerializedName("first_air_date") private String firstAirDate;

    @SerializedName("overview") private String overview;
    @SerializedName("known_for") private ArrayList<Movie> knownFor;

    @SerializedName("poster_path") private String posterPath;
    @SerializedName("profile_path") private String profilePath;

    public SearchResult() {
    }

    public SearchResult(int id, String mediaType) {
        this.id = id;
        this.mediaType = mediaType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public ArrayList<Movie> getKnownFor() {
        return knownFor;
    }

    public void setKnownFor(ArrayList<Movie> knownFor) {
        this.knownFor = knownFor;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}

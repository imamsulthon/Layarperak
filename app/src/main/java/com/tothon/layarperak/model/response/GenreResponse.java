package com.tothon.layarperak.model.response;

import com.google.gson.annotations.SerializedName;
import com.tothon.layarperak.model.Genre;

import java.util.ArrayList;

public class GenreResponse {

    @SerializedName("genres")
    private ArrayList<Genre> genres;

    public GenreResponse(ArrayList<Genre> result) {
        this.genres = result;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }
}

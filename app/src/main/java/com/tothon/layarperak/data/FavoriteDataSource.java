package com.tothon.layarperak.data;

import com.tothon.layarperak.model.Movie;
import com.tothon.layarperak.model.Television;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class FavoriteDataSource {

    private Realm realm;

    public FavoriteDataSource() {
    }

    public void open() {
        realm = Realm.getDefaultInstance();
    }

    public void close() {
        realm.close();
    }

    public void addMovieToFavorite(final Movie movie) {
        realm.executeTransaction(realm1 -> realm.insertOrUpdate(movie));
    }

    public void deleteMovieFromFavorite(final Movie movie) {
        realm.executeTransaction(realm1 -> movie.deleteFromRealm());
    }

    public ArrayList<Movie> getAllFavoriteMovies() {
        RealmResults<Movie> movies = realm.where(Movie.class).findAll();
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        movieArrayList.addAll(realm.copyFromRealm(movies));
        return movieArrayList;
    }

    public Movie findMovieWithId(int id) {
        return realm.where(Movie.class).equalTo("id", id).findFirst();
    }

    public void addTelevisionToFavorite(final Television television) {
        realm.executeTransaction(realm1 -> realm.insertOrUpdate(television));
    }

    public void deleteTelevisionFromFavorite(final Television television) {
        realm.executeTransaction(realm1 -> television.deleteFromRealm());
    }

    public ArrayList<Television> getAllFavoriteTelevisions() {
        RealmResults<Television> televisions = realm.where(Television.class).findAll();
        ArrayList<Television> televisionArrayList = new ArrayList<>();
        televisionArrayList.addAll(realm.copyFromRealm(televisions));
        return televisionArrayList;
    }

    public Television findTelevisionWithId(int id) {
        return realm.where(Television.class).equalTo("id", id).findFirst();
    }

}

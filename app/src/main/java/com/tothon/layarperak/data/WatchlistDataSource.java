package com.tothon.layarperak.data;

import com.tothon.layarperak.model.Movie;
import com.tothon.layarperak.model.Television;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class WatchlistDataSource {

    private Realm realm;

    public WatchlistDataSource() {
    }

    public void open() {
        realm = Realm.getDefaultInstance();
    }

    public void close() {
        realm.close();
    }

    public void addMovieToWatchlist(final Movie movie) {
        realm.executeTransaction(realm1 -> realm.insertOrUpdate(movie));
    }

    public void deleteMovieFromWatchlist(final Movie movie) {
        realm.executeTransaction(realm1 -> movie.deleteFromRealm());
    }

    public ArrayList<Movie> getAllWatchlistMovies() {
        RealmResults<Movie> movies = realm.where(Movie.class).findAll();
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        movieArrayList.addAll(realm.copyFromRealm(movies));
        return movieArrayList;
    }

    public Movie findWatchlistMovieWithId(int id) {
        return realm.where(Movie.class).equalTo("id", id).findFirst();
    }


    public void addTelevisionToWatchlist(final Television television) {
        realm.executeTransaction(realm1 -> realm.insertOrUpdate(television));
    }

    public void deleteTelevisionFromWatchlist(final Television television) {
        realm.executeTransaction(realm1 -> television.deleteFromRealm());
    }

    public ArrayList<Television> getAllWatchlistTelevisions() {
        RealmResults<Television> televisions = realm.where(Television.class).findAll();
        ArrayList<Television> televisionArrayList = new ArrayList<>();
        televisionArrayList.addAll(realm.copyFromRealm(televisions));
        return televisionArrayList;
    }

    public Television findWatchlistTelevisionWithId(int id) {
        return realm.where(Television.class).equalTo("id", id).findFirst();
    }

}

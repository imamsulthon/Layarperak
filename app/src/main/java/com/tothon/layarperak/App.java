package com.tothon.layarperak;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

    RealmConfiguration realmConfigurationFav, realmConfigurationWatchlist;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        realmConfigurationFav = new RealmConfiguration.Builder()
                .name("lpFavorite.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        realmConfigurationWatchlist = new RealmConfiguration.Builder()
                .name("lpWatchlist.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
    }
}

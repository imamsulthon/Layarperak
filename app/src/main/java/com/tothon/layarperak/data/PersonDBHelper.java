package com.tothon.layarperak.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PersonDBHelper extends SQLiteOpenHelper {

    public static final String TABLE_PERSON = "favorite_person";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PLACE_OF_BIRTH = "place_of_birth";
    public static final String COLUMN_BIRTHDAY = "birthday";
    public static final String COLUMN_DEATHDAY = "deathday";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_BIOGRAPHY = "biography";
    public static final String COLUMN_POPULARITY = "popularity";
    public static final String COLUMN_KNOWN_FOR_DEPT = "known_for_department";
    public static final String COLUMN_PROFILE_PATH = "profile_path";
    public static final String COLUMN_IMDB_ID = "imdb_id";
    public static final String COLUMN_HOME_PAGE = "homepage";

    private static final String DB_NAME = "favperson.db";
    private static final int DB_VERSION = 1;

    public PersonDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table "
                + TABLE_PERSON + "("
                + COLUMN_ID + " integer, "
                + COLUMN_NAME + " text not null, "
                + COLUMN_PLACE_OF_BIRTH + " text, "
                + COLUMN_BIRTHDAY + " text, "
                + COLUMN_DEATHDAY + " text, "
                + COLUMN_GENDER + " integer, "
                + COLUMN_BIOGRAPHY + " text, "
                + COLUMN_POPULARITY + " real, "
                + COLUMN_KNOWN_FOR_DEPT + " text, "
                + COLUMN_PROFILE_PATH + " text, "
                + COLUMN_IMDB_ID + " text, "
                + COLUMN_HOME_PAGE + " text);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
        onCreate(db);
    }
}

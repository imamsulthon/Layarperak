package com.tothon.layarperak.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.tothon.layarperak.model.Person;

import java.util.ArrayList;

public class PersonDataSource {

    private SQLiteDatabase database;
    private PersonDBHelper dbHelper;
    private Context context;

    private String[] allColumns = {
            PersonDBHelper.COLUMN_ID,
            PersonDBHelper.COLUMN_NAME,
            PersonDBHelper.COLUMN_PLACE_OF_BIRTH,
            PersonDBHelper.COLUMN_BIRTHDAY,
            PersonDBHelper.COLUMN_DEATHDAY,
            PersonDBHelper.COLUMN_GENDER,
            PersonDBHelper.COLUMN_BIOGRAPHY,
            PersonDBHelper.COLUMN_POPULARITY,
            PersonDBHelper.COLUMN_KNOWN_FOR_DEPT,
            PersonDBHelper.COLUMN_PROFILE_PATH,
            PersonDBHelper.COLUMN_IMDB_ID,
            PersonDBHelper.COLUMN_HOME_PAGE,
    };

    public PersonDataSource(Context context) {
        dbHelper = new PersonDBHelper(context);
        this.context = context;
    }

    public void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addFavoritePerson(Person person) {
        ContentValues values = new ContentValues();
        values.put(PersonDBHelper.COLUMN_ID, person.getId());
        values.put(PersonDBHelper.COLUMN_NAME, person.getName());
        values.put(PersonDBHelper.COLUMN_PLACE_OF_BIRTH, person.getPlaceOfBirth());
        values.put(PersonDBHelper.COLUMN_BIRTHDAY, person.getBirthday());
        values.put(PersonDBHelper.COLUMN_DEATHDAY, person.getDeathday());
        values.put(PersonDBHelper.COLUMN_GENDER, person.getGender());
        values.put(PersonDBHelper.COLUMN_BIOGRAPHY, person.getBiography());
        values.put(PersonDBHelper.COLUMN_POPULARITY, person.getPopularity());
        values.put(PersonDBHelper.COLUMN_KNOWN_FOR_DEPT, person.getDepartment());
        values.put(PersonDBHelper.COLUMN_PROFILE_PATH, person.getProfilePath());
        values.put(PersonDBHelper.COLUMN_IMDB_ID, person.getImdbId());
        values.put(PersonDBHelper.COLUMN_HOME_PAGE, person.getHomepage());
        database.insert(PersonDBHelper.TABLE_PERSON, null, values);
    }

    public ArrayList<Person> getAllFavoritePerson() {
        ArrayList<Person> personList = new ArrayList<>();
        Cursor cursor = database.query(PersonDBHelper.TABLE_PERSON, allColumns, null, null,
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Person person = cursorToPerson(cursor);
            personList.add(person);
            cursor.moveToNext();
        }
        cursor.close();
        return personList;
    }

    public void removePersonFromFavorite(Person person) {
        database.delete(PersonDBHelper.TABLE_PERSON, PersonDBHelper.COLUMN_ID + " = "
                + person.getId(), null);
    }

    public Person findPersonWithId(int id) {
        Person person = new Person();
        ArrayList<Person> personArrayList = getAllFavoritePerson();
        if (personArrayList.size() != 0) {
            for (Person p: personArrayList) {
                if (p.getId() == id) {
                    person = new Person(
                            p.getId(),
                            p.getName(),
                            p.getBirthday(),
                            p.getDeathday(),
                            p.getGender(),
                            p.getDepartment(),
                            p.getBiography(),
                            p.getPopularity(),
                            p.getProfilePath(),
                            p.getPlaceOfBirth(),
                            p.getImdbId(),
                            p.getHomepage());
                    break;
                }
            }
        }
        return person;
    }

    public Person findPerson(int id) {
        Person person = new Person();
        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT * FROM " + PersonDBHelper.TABLE_PERSON + " WHERE id = " + id, null);
            person = cursorToPerson(cursor);
        }
        return person;
    }

    private Person cursorToPerson(Cursor cursor) {
        Person person = new Person();
        person.setId(cursor.getInt(0));
        person.setName(cursor.getString(1));
        person.setPlaceOfBirth(cursor.getString(2));
        person.setBirthday(cursor.getString(3));
        person.setDeathday(cursor.getString(4));
        person.setGender(cursor.getInt(5));
        person.setBiography(cursor.getString(6));
        person.setPopularity(cursor.getDouble(7));
        person.setDepartment(cursor.getString(8));
        person.setProfilePath(cursor.getString(9));
        person.setImdbId(cursor.getString(10));
        person.setHomepage(cursor.getString(11));
        return person;
    }
}

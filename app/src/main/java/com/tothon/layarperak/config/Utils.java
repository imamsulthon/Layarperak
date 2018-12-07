package com.tothon.layarperak.config;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public Utils() {
    }

    public static String formatDate(String jsonDate) {
        DateFormat sourceDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sourceDateFormat.parse(jsonDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat destDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        String dateStr = destDateFormat.format(date);
        return dateStr;
    }


    public static String convertGenderString(int id) {
        String gender = null;
        if (id == 1) {
            gender = "Female";
        } else  if (id == 2) {
            gender = "Male";
        } else {
            gender = "Unidentified";
        }
        String result = gender;
        return result;
    }

}

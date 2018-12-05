package com.tothon.layarperak.config;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.res.ResourcesCompat;

import com.tothon.layarperak.R;

public class Config {

    private Context context;

    public Config() {
    }

    public Config(Context context) {
        this.context = context;
    }

    public Typeface getTypeface() {
        return ResourcesCompat.getFont(context, R.font.metrophobic);
    }

}
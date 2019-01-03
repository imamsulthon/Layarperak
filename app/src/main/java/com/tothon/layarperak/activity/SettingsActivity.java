package com.tothon.layarperak.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.tothon.layarperak.R;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.brand)
    TextView brand;
    @BindView(R.id.model)
    TextView model;
    @BindView(R.id.sdk)
    TextView sdk;
    @BindView(R.id.code)
    TextView code;
    @BindView(R.id.board)
    TextView board;
    @BindView(R.id.display)
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        getDeviceManufactureDetails();
    }

    private void getDeviceManufactureDetails() {
        PackageInfo packageInfo = null;
        String versionName = null;
        int versionCode = 0;
        try {
            packageInfo = getPackageManager().getPackageInfo(this.getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Field[] fields = Build.VERSION_CODES.class.getFields();
        String osName = fields[Build.VERSION.SDK_INT + 1].getName();

        brand.setText("Brand: " + Build.BRAND);
        model.setText("Model: " + Build.MODEL);
        sdk.setText("SDK Version: " + Build.VERSION.SDK + ", Operating System: " + osName);
        code.setText("Code: " + Build.VERSION.RELEASE);
        board.setText("Board: " + Build.BOARD);
        display.setText("Display: " + Build.DISPLAY);

    }
}

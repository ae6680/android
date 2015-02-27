package com.shinav.mathapp;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

public class MyApplication extends Application {

    private static Context context;
    public static int screenHeight;
    public static int screenWidth;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
    }

    public static Context getAppContext() {
        return context;
    }
}

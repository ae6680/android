package com.shinav.mathapp;

import android.app.Application;
import android.util.DisplayMetrics;

import com.firebase.client.Firebase;

import timber.log.Timber;

public class MyApplication extends Application {

    public static String PREF = "com.shinav.mathapp";
    public static String PREF_CHOSEN_CHARACTER = PREF + ".chosen_character";
    public static String PREF_TUTORIAL_COMPLETED = PREF + ".tutorial_completed";

    public static int screenHeight;
    public static int screenWidth;

    private boolean mockMode = false;

    @Override public void onCreate() {
        super.onCreate();

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        setupFirebase();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
//            Timber.plant(new CrashlyticsTree());
//            Fabric.with(this, new Crashlytics());
        }
    }

    private void setupFirebase() {
        Firebase.setAndroidContext(this);
    }

    public void setMockMode(boolean provideMocks) {
        mockMode = provideMocks;
    }

    public boolean isMockMode() {
        return mockMode;
    }
}

package com.shinav.mathapp;

import android.app.Application;
import android.util.DisplayMetrics;

import com.firebase.client.Firebase;
import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.shinav.mathapp.reporting.ParseTree;

import timber.log.Timber;

public class MyApplication extends Application {

    public static String PREF = "com.shinav.mathapp";
    public static String PREF_CHOSEN_CHARACTER = PREF + ".chosen_character";

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
            Timber.plant(new ParseTree());
            ParseCrashReporting.enable(this);
        }

        // Testing crash reporting
//        Timber.plant(new ParseTree());
//        ParseCrashReporting.enable(this);

        setupParse();

        // Testing crash reporting
//        Timber.e("Message", "HOI");
//        throw new RuntimeException("Test Exception!");
    }

    private void setupParse() {
        Parse.initialize(
                this,
                getString(R.string.parse_application_id),
                getString(R.string.parse_client_key)
        );
        ParseUser.enableAutomaticUser();
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.saveInBackground();
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

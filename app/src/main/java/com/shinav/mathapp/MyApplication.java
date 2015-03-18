package com.shinav.mathapp;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.firebase.client.Firebase;
import com.shinav.mathapp.injection.AndroidModule;
import com.shinav.mathapp.sync.FirebaseRealmAdapter;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

public class MyApplication extends Application {

    private static Context context;
    public static int screenHeight;
    public static int screenWidth;

    private ObjectGraph applicationGraph;

    @Override public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        setupFirebase();

        applicationGraph = ObjectGraph.create(getModules().toArray());
    }

    private void setupFirebase() {
        Firebase.setAndroidContext(this);

        FirebaseRealmAdapter.getInstance().register();
    }

    protected List getModules() {
        return Arrays.asList(
                new AndroidModule(this)
        );
    }

    public <T> void inject(T instance) {
        applicationGraph.inject(instance);
    }

    public static Context getAppContext() {
        return context;
    }
}

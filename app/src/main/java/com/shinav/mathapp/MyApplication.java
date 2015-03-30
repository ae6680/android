package com.shinav.mathapp;

import android.app.Application;
import android.util.DisplayMetrics;

import com.firebase.client.Firebase;
import com.shinav.mathapp.injection.modules.AndroidModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

public class MyApplication extends Application {

    public static int screenHeight;
    public static int screenWidth;

    private ObjectGraph applicationGraph;

    @Override public void onCreate() {
        super.onCreate();

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        setupFirebase();

        applicationGraph = ObjectGraph.create(getModules().toArray());
    }

    private void setupFirebase() {
        Firebase.setAndroidContext(this);
    }

    protected List getModules() {
        return Arrays.asList(
                new AndroidModule(this)
        );
    }

    public ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }

    public <T> void inject(T instance) {
        applicationGraph.inject(instance);
    }

}

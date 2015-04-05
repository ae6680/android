package com.shinav.mathapp;

import android.app.Application;
import android.util.DisplayMetrics;

import com.firebase.client.Firebase;
import com.shinav.mathapp.injection.component.ApplicationComponent;
import com.shinav.mathapp.injection.component.Dagger_ApplicationComponent;
import com.shinav.mathapp.injection.module.ApplicationModule;

public class MyApplication extends Application {

    public static int screenHeight;
    public static int screenWidth;

    private static ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        setupFirebase();

        initializeComponent();
    }

    private void setupFirebase() {
        Firebase.setAndroidContext(this);
    }

    protected void initializeComponent() {
        component = Dagger_ApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static ApplicationComponent getComponent() {
        return component;
    }

}

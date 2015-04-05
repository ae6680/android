package com.shinav.mathapp;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.firebase.client.Firebase;
import com.shinav.mathapp.injection.component.ActivityComponent;
import com.shinav.mathapp.injection.component.ApplicationComponent;
import com.shinav.mathapp.injection.component.Dagger_ActivityComponent;
import com.shinav.mathapp.injection.component.Dagger_ApplicationComponent;
import com.shinav.mathapp.injection.module.ActivityModule;
import com.shinav.mathapp.injection.module.ApplicationModule;

public class MyApplication extends Application {

    public static int screenHeight;
    public static int screenWidth;

    private ApplicationComponent component;

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

    public ApplicationComponent getComponent() {
        return component;
    }

    public ActivityComponent getActivityComponent(Context context) {
        return Dagger_ActivityComponent.builder()
                .applicationComponent(getComponent())
                .activityModule(new ActivityModule(context))
                .build();
    }

}

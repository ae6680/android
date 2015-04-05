package com.shinav.mathapp.injection.module;

import android.content.Context;

import com.shinav.mathapp.injection.annotation.ForActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final Context context;

    public ActivityModule(Context context) {
        this.context = context;
    }

    @Provides @ForActivity Context provideActivityContext() {
        return context;
    }

}

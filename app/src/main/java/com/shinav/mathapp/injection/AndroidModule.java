package com.shinav.mathapp.injection;

import android.content.Context;

import com.firebase.client.Firebase;
import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.calculator.CalculatorFragment;
import com.shinav.mathapp.firebase.FirebaseParser;
import com.shinav.mathapp.firebase.listeners.FirebaseListener;
import com.shinav.mathapp.sync.FirebaseChildRegisterer;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module(
        injects = {
                CalculatorFragment.class,
                FirebaseChildRegisterer.class,
                FirebaseListener.class
        },
        library = true
)
public class AndroidModule {

    private final MyApplication application;

    public AndroidModule(MyApplication application) {
        this.application = application;
    }

    @Provides @Singleton Bus provideBus() {
        return new Bus(ThreadEnforcer.ANY);
    }

    @Provides @Singleton Realm provideRealm() {
        return Realm.getInstance(application);
    }

    @Provides @Singleton FirebaseParser provideFirebaseParser() {
        return new FirebaseParser();
    }

    @Provides @Singleton Firebase provideFirebase() {
        return new Firebase("https://arithmetic-exam-app.firebaseio.com/");
    }

    @Provides @ForApplication Context provideApplicationContext() {
        return application.getApplicationContext();
    }

}

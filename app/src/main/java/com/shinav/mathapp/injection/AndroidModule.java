package com.shinav.mathapp.injection;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.calculator.CalculatorFragment;
import com.shinav.mathapp.firebase.FirebaseParser;
import com.shinav.mathapp.question.QuestionActivity;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module(injects = {
        CalculatorFragment.class,
        QuestionActivity.class,
}, library = true)
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

}

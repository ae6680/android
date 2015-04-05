package com.shinav.mathapp.injection.module;

import android.content.Context;

import com.firebase.client.Firebase;
import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.calculator.CalculatorFragment;
import com.shinav.mathapp.db.helper.DbOpenHelper;
import com.shinav.mathapp.firebase.FirebaseChildRegisterer;
import com.shinav.mathapp.firebase.FirebaseParser;
import com.shinav.mathapp.injection.annotation.ForApplication;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                CalculatorFragment.class,
                FirebaseChildRegisterer.class,
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

    @Provides @Singleton FirebaseParser provideFirebaseParser() {
        return new FirebaseParser();
    }

    @Provides @Singleton Firebase provideFirebase() {
        return new Firebase("https://arithmetic-exam-app.firebaseio.com/");
    }

    @Provides @Singleton DbOpenHelper provideDbOpenHelper(@ForApplication Context context) {
        return new DbOpenHelper(context);
    }

    @Provides @Singleton SqlBrite provideSqlBrite(DbOpenHelper helper) {
        return SqlBrite.create(helper);
    }

    @Provides @ForApplication Context provideApplicationContext() {
        return application.getApplicationContext();
    }

}

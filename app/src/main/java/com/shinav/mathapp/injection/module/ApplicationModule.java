package com.shinav.mathapp.injection.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.firebase.client.Firebase;
import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.db.helper.DbOpenHelper;
import com.shinav.mathapp.firebase.FirebaseParser;
import com.shinav.mathapp.injection.annotation.ForApplication;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Context applicationContext;

    public ApplicationModule(Context applicationContext) {
        this.applicationContext = applicationContext;
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
        return applicationContext;
    }

    @Provides SharedPreferences provideSharedPreferences(@ForApplication Context context) {
        return context.getSharedPreferences(MyApplication.PREF, Context.MODE_PRIVATE);
    }

}

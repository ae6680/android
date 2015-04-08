package com.shinav.mathapp.injection.component;

import com.firebase.client.Firebase;
import com.shinav.mathapp.db.helper.DbOpenHelper;
import com.shinav.mathapp.firebase.FirebaseChildRegisterer;
import com.shinav.mathapp.firebase.FirebaseParser;
import com.shinav.mathapp.injection.annotation.ForApplication;
import com.shinav.mathapp.injection.module.ApplicationModule;
import com.shinav.mathapp.storytelling.StorytellingService;
import com.shinav.mathapp.tutorial.TutorialManagingService;
import com.squareup.otto.Bus;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@ForApplication
@Component(modules = {
        ApplicationModule.class,
})
public interface ApplicationComponent {

    Bus bus();
    FirebaseParser firebaseParser();
    Firebase firebase();
    DbOpenHelper dbOpenHelper();
    SqlBrite sqlBrite();

    void inject(FirebaseChildRegisterer firebaseChildRegisterer);
    void inject(TutorialManagingService tutorialManagingService);
    void inject(StorytellingService storytellingService);
}

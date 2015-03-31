package com.shinav.mathapp.injection.module;

import android.app.Activity;
import android.content.Context;

import com.shinav.mathapp.approach.ApproachActivity;
import com.shinav.mathapp.approach.ApproachDragRecyclerView;
import com.shinav.mathapp.approach.feedback.ApproachFeedbackActivity;
import com.shinav.mathapp.conversation.ConversationActivity;
import com.shinav.mathapp.injection.annotation.ForActivity;
import com.shinav.mathapp.main.MainActivity;
import com.shinav.mathapp.question.QuestionActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                MainActivity.class,
                ConversationActivity.class,
                ApproachActivity.class,
                ApproachFeedbackActivity.class,
                QuestionActivity.class,
                ApproachDragRecyclerView.class,
        },
        complete = false,
        library = true
)
public class ActivityModule {

    private Activity activity;

    public ActivityModule() { }

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides @Singleton @ForActivity Context provideActivityContext() {
        return activity;
    }
}

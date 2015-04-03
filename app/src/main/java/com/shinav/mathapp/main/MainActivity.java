package com.shinav.mathapp.main;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.mapper.StoryProgressPartListMapper;
import com.shinav.mathapp.db.model.StoryProgress;
import com.shinav.mathapp.db.model.StoryProgressPart;
import com.shinav.mathapp.event.MakeQuestionButtonClicked;
import com.shinav.mathapp.injection.InjectedActionBarActivity;
import com.shinav.mathapp.injection.module.ActivityModule;
import com.shinav.mathapp.main.practice.PracticeOverviewView;
import com.shinav.mathapp.main.storyProgress.StoryProgressView;
import com.shinav.mathapp.progress.Storyteller;
import com.shinav.mathapp.question.QuestionActivity;
import com.shinav.mathapp.sync.FirebaseChildRegisterer;
import com.shinav.mathapp.tab.TabsView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class MainActivity extends InjectedActionBarActivity {

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.tabs_view) TabsView tabsView;

    @Inject Bus bus;
    @Inject FirebaseChildRegisterer registerer;
    @Inject SqlBrite db;

    @Inject StoryProgressView storyProgressView;
    @Inject PracticeOverviewView practiceOverviewView;
    private Subscription storyProgressSubscription;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        registerer.register();

        initToolbar();
        initTabs();
    }

    @Override public ActivityModule getModules() {
        return new ActivityModule(this);
    }

    @Override public void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Override protected void onResume() {
        super.onResume();
        loadStoryProgress();
    }

    @Override protected void onPause() {
        super.onPause();
        storyProgressSubscription.unsubscribe();
    }

    private void initToolbar() {
        toolbar.setTitle(R.string.main_toolbar_title);
        setSupportActionBar(toolbar);
    }

    private void initTabs() {
        tabsView.addTab(getResources().getString(R.string.main_tab_1), storyProgressView);
        tabsView.addTab(getResources().getString(R.string.main_tab_2), practiceOverviewView);
    }

    private void loadStoryProgress() {

        String storyProgressKey;

        // For now
        Cursor c = db.query("SELECT * FROM " + Tables.StoryProgress.TABLE_NAME);
        if (c.moveToFirst()) {
            storyProgressKey = StoryProgress.fromCursor(c).getKey();
        } else {
            StoryProgress storyProgress = new StoryProgress();
            storyProgress.setKey(UUID.randomUUID().toString());

            String questionKey = "question-1";

            StoryProgressPart storyProgressPart = new StoryProgressPart();
            storyProgressPart.setKey(UUID.randomUUID().toString());
            storyProgressPart.setStoryProgressKey(storyProgress.getKey());
            storyProgressPart.setQuestionKey(questionKey);

            db.insert(Tables.StoryProgress.TABLE_NAME, storyProgress.getContentValues());
            db.insert(Tables.StoryProgressPart.TABLE_NAME, storyProgressPart.getContentValues());

            storyProgressKey = storyProgress.getKey();
        }
        c.close();

        storyProgressSubscription = db.createQuery(
                Tables.StoryProgressPart.TABLE_NAME,
                "SELECT * FROM " + Tables.StoryProgressPart.TABLE_NAME +
                        " WHERE " + Tables.StoryProgressPart.STORY_PROGRESS_KEY + " = ?"
                , storyProgressKey
        )
                .map(new StoryProgressPartListMapper())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<StoryProgressPart>>() {
                    @Override public void call(List<StoryProgressPart> storyProgressParts) {
                        storyProgressView.setStoryProgressParts(storyProgressParts);
                    }
                });
    }

    @Subscribe public void onMakeQuestionButtonClicked(MakeQuestionButtonClicked event) {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra(Storyteller.TYPE_KEY, event.getQuestionFirebaseKey());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_left_from_outside, R.anim.slide_left_to_outside);
    }

}

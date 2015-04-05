package com.shinav.mathapp.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.mapper.StoryProgressMapper;
import com.shinav.mathapp.db.mapper.StoryProgressPartMapper;
import com.shinav.mathapp.db.pojo.StoryProgress;
import com.shinav.mathapp.db.pojo.StoryProgressPart;
import com.shinav.mathapp.event.MakeQuestionButtonClicked;
import com.shinav.mathapp.firebase.FirebaseChildRegisterer;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.main.practice.PracticeOverviewView;
import com.shinav.mathapp.main.storyProgress.StoryProgressView;
import com.shinav.mathapp.progress.Storyteller;
import com.shinav.mathapp.question.QuestionActivity;
import com.shinav.mathapp.tab.TabsView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.tabs_view) TabsView tabsView;

    @Inject Bus bus;
    @Inject FirebaseChildRegisterer registerer;

    @Inject StoryProgressMapper storyProgressMapper;
    @Inject StoryProgressPartMapper storyProgressPartMapper;

    @Inject StoryProgressView storyProgressView;
    @Inject PracticeOverviewView practiceOverviewView;

    private Subscription storyProgressSubscription;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        ComponentFactory.getActivityComponent(this).inject(this);

        registerer.register();

        initToolbar();
        initTabs();
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
        List<StoryProgress> storyProgresses = storyProgressMapper.getAll();
        if (!storyProgresses.isEmpty()) {
            storyProgressKey = storyProgresses.get(0).getKey();
        } else {
            StoryProgress storyProgress = new StoryProgress();
            storyProgress.setKey(UUID.randomUUID().toString());

            String questionKey = "-Jm5gSBknl4_vzQLgxFB";

            StoryProgressPart storyProgressPart = new StoryProgressPart();
            storyProgressPart.setKey(UUID.randomUUID().toString());
            storyProgressPart.setStoryProgressKey(storyProgress.getKey());
            storyProgressPart.setQuestionKey(questionKey);
            storyProgressPart.setState(StoryProgressPart.STATE_UNMADE);
            storyProgressPart.setTitle("Title HOI");

            storyProgressMapper.insert(storyProgress);
            storyProgressPartMapper.insert(storyProgressPart);

            storyProgressKey = storyProgress.getKey();
        }

        storyProgressSubscription = storyProgressPartMapper.getByStoryProgressKey(
                storyProgressKey, new Action1<List<StoryProgressPart>>() {
                    @Override public void call(List<StoryProgressPart> storyProgressParts) {
                        Collections.reverse(storyProgressParts);
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

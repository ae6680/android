package com.shinav.mathapp.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.shinav.mathapp.R;
import com.shinav.mathapp.event.MakeQuestionButtonClicked;
import com.shinav.mathapp.injection.InjectedActionBarActivity;
import com.shinav.mathapp.injection.module.ActivityModule;
import com.shinav.mathapp.main.practice.PracticeOverviewView;
import com.shinav.mathapp.main.storyProgress.StoryProgress;
import com.shinav.mathapp.main.storyProgress.StoryProgressPart;
import com.shinav.mathapp.main.storyProgress.StoryProgressView;
import com.shinav.mathapp.progress.Storyteller;
import com.shinav.mathapp.question.QuestionActivity;
import com.shinav.mathapp.repository.RealmRepository;
import com.shinav.mathapp.sync.FirebaseChildRegisterer;
import com.shinav.mathapp.tab.TabsView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmList;

public class MainActivity extends InjectedActionBarActivity {

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.tabs_view) TabsView tabsView;

    @Inject Bus bus;
    @Inject FirebaseChildRegisterer registerer;
    @Inject RealmRepository realmRepository;
    @Inject Realm realm;

    @Inject StoryProgressView storyProgressView;
    @Inject PracticeOverviewView practiceOverviewView;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        registerer.register();

        initToolbar();
        initTabs();
        initStoryRecyclerView();
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

    private void initToolbar() {
        toolbar.setTitle(R.string.main_toolbar_title);
        setSupportActionBar(toolbar);
    }

    private void initTabs() {
        tabsView.addTab(getResources().getString(R.string.main_tab_1), storyProgressView);
        tabsView.addTab(getResources().getString(R.string.main_tab_2), practiceOverviewView);
    }

    private void initStoryRecyclerView() {
        StoryProgress storyProgress = realmRepository.getStoryProgress();

        // For now
        if (storyProgress == null) {
            storyProgress = createStoryProgress();
            realm.beginTransaction();
            realm.copyToRealm(storyProgress);
            realm.commitTransaction();
            storyProgress = realmRepository.getStoryProgress();
        }

        storyProgressView.setStoryProgress(storyProgress);
    }

    private StoryProgress createStoryProgress() {

        String questionKey = "question-1";

        StoryProgressPart storyProgressPart = new StoryProgressPart();
        storyProgressPart.setIdentifier(UUID.randomUUID().toString());
        storyProgressPart.setQuestionKey(questionKey);

        StoryProgress storyProgress = new StoryProgress();
        storyProgress.setIdentifier(UUID.randomUUID().toString());

        RealmList<StoryProgressPart> realmList = new RealmList<>();
        realmList.add(storyProgressPart);

        storyProgress.setStoryProgressParts(realmList);

        return storyProgress;
    }

    @Subscribe public void onMakeQuestionButtonClicked(MakeQuestionButtonClicked event) {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra(Storyteller.TYPE_KEY, event.getQuestionFirebaseKey());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_left_from_outside, R.anim.slide_left_to_outside);
    }

}

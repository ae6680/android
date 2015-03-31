package com.shinav.mathapp.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.shinav.mathapp.R;
import com.shinav.mathapp.injection.InjectedActionBarActivity;
import com.shinav.mathapp.injection.modules.ActivityModule;
import com.shinav.mathapp.main.practice.PracticeOverviewView;
import com.shinav.mathapp.main.storyProgress.StoryProgressView;
import com.shinav.mathapp.repository.RealmRepository;
import com.shinav.mathapp.story.Story;
import com.shinav.mathapp.story.StoryEntry;
import com.shinav.mathapp.sync.FirebaseChildRegisterer;
import com.shinav.mathapp.view.TabsView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends InjectedActionBarActivity {

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.tabs_view) TabsView tabsView;

    @Inject FirebaseChildRegisterer registerer;
    @Inject RealmRepository realmRepository;

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

    private void initToolbar() {
        toolbar.setTitle(R.string.main_toolbar_title);
        setSupportActionBar(toolbar);
    }

    private void initTabs() {
        tabsView.addTab(getResources().getString(R.string.main_tab_1), storyProgressView);
        tabsView.addTab(getResources().getString(R.string.main_tab_2), practiceOverviewView);
    }

    private void initStoryRecyclerView() {
        Story story = realmRepository.getStory("story-0");
        storyProgressView.setStoryEntries(filterOnQuestionType(story.getStoryEntries()));
    }

    private ArrayList<StoryEntry> filterOnQuestionType(List<StoryEntry> storyEntries) {
        ArrayList<StoryEntry> questionStories = new ArrayList<>();
        for (StoryEntry storyEntry : storyEntries) {
            if (storyEntry.isQuestion()) {
                questionStories.add(storyEntry);
            }
        }
        return questionStories;
    }

}

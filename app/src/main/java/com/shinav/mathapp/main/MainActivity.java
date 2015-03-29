package com.shinav.mathapp.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.shinav.mathapp.R;
import com.shinav.mathapp.injection.ActivityModule;
import com.shinav.mathapp.injection.InjectedActionBarActivity;
import com.shinav.mathapp.repository.RealmRepository;
import com.shinav.mathapp.story.Story;
import com.shinav.mathapp.story.StoryEntry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends InjectedActionBarActivity {

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.story_card_recycler_view) StoryCardRecyclerView storyCardRecyclerView;

    @Inject RealmRepository realmRepository;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initToolbar();
        initStoryRecyclerView();
    }

    @Override public ActivityModule getModules() {
        return new ActivityModule(this);
    }

    private void initToolbar() {
        toolbar.setTitle(R.string.main_toolbar_title);
        setSupportActionBar(toolbar);
    }

    private void initStoryRecyclerView() {
        Story story = realmRepository.getStory("story-0");
        storyCardRecyclerView.setStoryEntries(filterOnQuestionType(story.getStoryEntries()));
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

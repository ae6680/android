package com.shinav.mathapp.main.storyProgress;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.shinav.mathapp.injection.modules.ViewModule;
import com.shinav.mathapp.story.StoryEntry;

import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;

public class StoryCardRecyclerView extends RecyclerView {

    @Inject StoryCardAdapter storyCardAdapter;

    public StoryCardRecyclerView(Context context) {
        super(context);
        init();
    }

    public StoryCardRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StoryCardRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

        ObjectGraph.create(new ViewModule()).inject(this);

        setAdapter(storyCardAdapter);
        setLayoutManager(new LinearLayoutManager(this.getContext()));

    }

    public void setStoryEntries(List<StoryEntry> storyEntries) {
        storyCardAdapter.setStoryEntries(storyEntries);
    }

}

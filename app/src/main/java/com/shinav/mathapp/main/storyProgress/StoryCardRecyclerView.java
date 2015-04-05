package com.shinav.mathapp.main.storyProgress;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.shinav.mathapp.db.pojo.StoryProgressPart;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

public class StoryCardRecyclerView extends RecyclerView {

    @Inject Bus bus;
    @Inject StoryProgressPartAdapter storyProgressPartAdapter;

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
        ComponentFactory.getViewComponent().inject(this);

        setAdapter(storyProgressPartAdapter);
        setLayoutManager(new LinearLayoutManager(this.getContext()));

    }

    public void setStoryProgressParts(List<StoryProgressPart> storyProgressParts) {
        storyProgressPartAdapter.setStoryProgressParts(storyProgressParts);
    }

}

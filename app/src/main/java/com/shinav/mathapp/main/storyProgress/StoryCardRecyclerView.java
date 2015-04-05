package com.shinav.mathapp.main.storyProgress;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.db.pojo.StoryProgressPart;
import com.shinav.mathapp.injection.module.AndroidModule;
import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

public class StoryCardRecyclerView extends RecyclerView {

    @Inject Bus bus;
    @Inject StoryProgressPartAdapter storyProgressPartAdapter;

    public StoryCardRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public StoryCardRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StoryCardRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {

        ObjectGraph graph = ((MyApplication) ((Activity) context).getApplication()).getApplicationGraph();
        graph.plus(new CustomModule()).inject(this);

        setAdapter(storyProgressPartAdapter);
        setLayoutManager(new LinearLayoutManager(this.getContext()));

    }

    public void setStoryProgressParts(List<StoryProgressPart> storyProgressParts) {
        storyProgressPartAdapter.setStoryProgressParts(storyProgressParts);
    }

    @Module(injects = StoryCardRecyclerView.class, addsTo = AndroidModule.class, library = true)
    public class CustomModule {

        @Provides public StoryProgressPartAdapter provideStoryQuestionCardAdapter() {
            return new StoryProgressPartAdapter(bus);
        }

    }
}

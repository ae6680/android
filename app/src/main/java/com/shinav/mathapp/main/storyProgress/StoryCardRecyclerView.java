package com.shinav.mathapp.main.storyProgress;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.injection.module.AndroidModule;
import com.shinav.mathapp.question.Question;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

public class StoryCardRecyclerView extends RecyclerView {

    @Inject Bus bus;
    @Inject StoryQuestionCardAdapter storyQuestionCardAdapter;

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

        setAdapter(storyQuestionCardAdapter);
        setLayoutManager(new LinearLayoutManager(this.getContext()));

    }

    public void setStoryProgressParts(List<StoryProgressPart> storyProgressParts) {
        List<Question> questions = new ArrayList<>();

        for (StoryProgressPart part : storyProgressParts) {
            questions.add(part.getQuestion());
        }

        storyQuestionCardAdapter.setQuestions(questions);
    }

    @Module(injects = StoryCardRecyclerView.class, addsTo = AndroidModule.class, library = true)
    public class CustomModule {

        @Provides public StoryQuestionCardAdapter provideStoryQuestionCardAdapter() {
            return new StoryQuestionCardAdapter(bus);
        }

    }
}

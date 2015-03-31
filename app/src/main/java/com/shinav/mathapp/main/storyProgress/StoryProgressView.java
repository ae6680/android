package com.shinav.mathapp.main.storyProgress;

import android.content.Context;
import android.util.AttributeSet;

import com.shinav.mathapp.R;
import com.shinav.mathapp.injection.annotation.ForActivity;
import com.shinav.mathapp.view.ButterKnifeLayout;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.InjectView;

public class StoryProgressView extends ButterKnifeLayout {

    @InjectView(R.id.story_card_recycler_view) StoryCardRecyclerView storyCardRecyclerView;

    @Inject
    public StoryProgressView(@ForActivity Context context) {
        super(context);
        init();
    }

    public StoryProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StoryProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(R.layout.story_progress_view, this, true);
    }

    public void setStoryProgress(StoryProgress storyProgress) {
        ArrayList<StoryProgressPart> parts = new ArrayList<>(storyProgress.getStoryProgressParts());
        storyCardRecyclerView.setStoryProgressParts(parts);
    }

}

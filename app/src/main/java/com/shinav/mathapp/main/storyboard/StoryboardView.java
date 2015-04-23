package com.shinav.mathapp.main.storyboard;

import android.content.Context;
import android.util.AttributeSet;

import com.shinav.mathapp.R;
import com.shinav.mathapp.injection.annotation.ForActivity;
import com.shinav.mathapp.view.ButterKnifeLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

public class StoryboardView extends ButterKnifeLayout {

    @InjectView(R.id.storyboard_frame_recycler_view)
    StoryboardFrameRecyclerView storyboardFrameRecyclerView;

    @Inject
    public StoryboardView(@ForActivity Context context) {
        super(context);
        init();
    }

    public StoryboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StoryboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(R.layout.storyboard_view, this, true);
    }

    public void setListItems(List<StoryboardFrameListItem> listItems) {
        storyboardFrameRecyclerView.setListItems(listItems);
    }

}

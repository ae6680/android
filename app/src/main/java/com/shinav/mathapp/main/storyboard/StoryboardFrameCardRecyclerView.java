package com.shinav.mathapp.main.storyboard;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.shinav.mathapp.injection.component.ComponentFactory;

import java.util.List;

import javax.inject.Inject;

public class StoryboardFrameCardRecyclerView extends RecyclerView {

    @Inject StoryboardFrameAdapter storyboardFrameAdapter;

    public StoryboardFrameCardRecyclerView(Context context) {
        super(context);
        init();
    }

    public StoryboardFrameCardRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StoryboardFrameCardRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        ComponentFactory.getViewComponent(this.getContext()).inject(this);

        setAdapter(storyboardFrameAdapter);
        setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    public void setListItems(List<StoryboardListItem> listItems) {
        storyboardFrameAdapter.setListItems(listItems);
    }

}

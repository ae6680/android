package com.shinav.mathapp.main.storyboard;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.shinav.mathapp.injection.component.Injector;

import java.util.List;

import javax.inject.Inject;

public class StoryboardFrameRecyclerView extends RecyclerView {

    @Inject StoryboardFrameAdapter storyboardFrameAdapter;

    public StoryboardFrameRecyclerView(Context context) {
        super(context);
        init();
    }

    public StoryboardFrameRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StoryboardFrameRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        Injector.getViewComponent(this.getContext()).inject(this);

        setAdapter(storyboardFrameAdapter);
        setLayoutManager(new GridLayoutManager(this.getContext(), 3));
    }

    public void setListItems(List<StoryboardFrameListItem> storyboardFrameListItems) {
        storyboardFrameAdapter.setListItems(storyboardFrameListItems);
    }

}

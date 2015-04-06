package com.shinav.mathapp.main.practice;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.shinav.mathapp.injection.component.ComponentFactory;

import javax.inject.Inject;

public class PracticeCardRecyclerView extends RecyclerView {

    @Inject PracticeCardAdapter practiceCardAdapter;

    public PracticeCardRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public PracticeCardRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PracticeCardRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        ComponentFactory.getViewComponent(context).inject(this);

        setAdapter(practiceCardAdapter);
        setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

}

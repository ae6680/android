package com.shinav.mathapp.main.practice;

import android.content.Context;
import android.util.AttributeSet;

import com.shinav.mathapp.R;
import com.shinav.mathapp.injection.annotation.ForActivity;
import com.shinav.mathapp.view.ButterKnifeLayout;

import javax.inject.Inject;

public class PracticeOverviewView extends ButterKnifeLayout {

    @Inject
    public PracticeOverviewView(@ForActivity Context context) {
        super(context);
        init();
    }

    public PracticeOverviewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PracticeOverviewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(R.layout.practice_overview_view, this, true);
    }

}

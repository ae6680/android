package com.shinav.mathapp.question;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.R;
import com.shinav.mathapp.animation.AnimationFactory;
import com.shinav.mathapp.approach.ApproachAdapter;
import com.shinav.mathapp.progress.ProgressProvider;
import com.shinav.mathapp.view.FlipCard;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class QuestionApproachView extends FlipCard {

    public static final float PERCENTAGE_HEIGHT = 0.38f;
    @InjectView(R.id.approach_list) RecyclerView approachList;

    public QuestionApproachView(Context context) {
        super(context);
        init();
    }

    public void init() {
        View view = LayoutInflater.from(this.getContext())
                .inflate(R.layout.question_approach_card, null, false);

        ButterKnife.inject(this, view);

        initApproachList();
        setParams(view);

        addView(view);
    }

    @OnClick(R.id.close)
    public void onCloseClicked() {
        flip(AnimationFactory.FlipDirection.LEFT_RIGHT, 300);
    }

    private void initApproachList() {
        ApproachAdapter approachAdapter = new ApproachAdapter();

        approachList.setAdapter(approachAdapter);
        approachList.setLayoutManager(new LinearLayoutManager(this.getContext()));

        approachAdapter.setApproaches(ProgressProvider.getCurrentQuestion().getApproaches());

        // Set layout
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                MyApplication.screenWidth,
                (int) (MyApplication.screenHeight * PERCENTAGE_HEIGHT)
        );
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        approachList.setLayoutParams(layoutParams);
    }

}

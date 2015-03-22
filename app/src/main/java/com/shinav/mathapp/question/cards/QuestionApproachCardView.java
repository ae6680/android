package com.shinav.mathapp.question.cards;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.R;
import com.shinav.mathapp.approach.Approach;
import com.shinav.mathapp.approach.ApproachAdapter;
import com.shinav.mathapp.view.Card;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class QuestionApproachCardView extends Card {

    public static final float PERCENTAGE_HEIGHT = 0.38f;

    @InjectView(R.id.approach_list) RecyclerView approachList;

    public QuestionApproachCardView(Context context) {
        super(context);
        init();
    }

    public void init() {
        View view = LayoutInflater.from(this.getContext())
                .inflate(R.layout.question_approach_card, this, false);
        ButterKnife.inject(this, view);

        setLayoutParams(view);

        addView(view);
    }

    private void setLayoutParams(View view) {
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                getDefaultCardHeight()
        );

        params.leftMargin = getResources().getDimensionPixelSize(R.dimen.card_pager_margin);
        params.rightMargin = getResources().getDimensionPixelSize(R.dimen.card_pager_margin);

        view.setLayoutParams(params);
    }

    public void setApproach(List<Approach> approaches) {
        initApproachList(approaches);
    }

    private void initApproachList(List<Approach> approaches) {
        ApproachAdapter approachAdapter = new ApproachAdapter();

        approachList.setAdapter(approachAdapter);
        approachList.setLayoutManager(new LinearLayoutManager(this.getContext()));

        approachAdapter.setApproaches(approaches);

        // Set layout
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                MyApplication.screenWidth,
                (int) (MyApplication.screenHeight * PERCENTAGE_HEIGHT)
        );
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        approachList.setLayoutParams(layoutParams);
    }

}

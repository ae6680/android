package com.shinav.mathapp.question.card;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.R;
import com.shinav.mathapp.approach.ApproachPartAdapter;
import com.shinav.mathapp.card.Card;
import com.shinav.mathapp.db.model.ApproachPart;
import com.shinav.mathapp.injection.annotation.ForActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

public class QuestionApproachCardView extends Card {

    public static final float PERCENTAGE_HEIGHT = 0.38f;

    @InjectView(R.id.approach_part_list) RecyclerView approachList;

    @Inject
    public QuestionApproachCardView(@ForActivity Context context) {
        super(context);
        init();
    }

    public void init() {
        View view = inflate(R.layout.question_approach_card, this, false);
        setLayoutParamsForViewPager(view);
        addView(view);
    }

    public void setApproachParts(List<ApproachPart> approachParts) {
        initApproachList(approachParts);
    }

    private void initApproachList(List<ApproachPart> approachParts) {
        ApproachPartAdapter approachPartAdapter = new ApproachPartAdapter();

        approachList.setAdapter(approachPartAdapter);
        approachList.setLayoutManager(new LinearLayoutManager(this.getContext()));

        approachPartAdapter.setApproachParts(approachParts);

        // Set layout
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                MyApplication.screenWidth,
                (int) (MyApplication.screenHeight * PERCENTAGE_HEIGHT)
        );
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        approachList.setLayoutParams(layoutParams);
    }

}

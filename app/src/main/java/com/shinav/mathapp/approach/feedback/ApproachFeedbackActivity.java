package com.shinav.mathapp.approach.feedback;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.R;
import com.shinav.mathapp.db.pojo.ApproachPart;
import com.shinav.mathapp.injection.InjectedActivity;
import com.shinav.mathapp.injection.module.ActivityModule;
import com.shinav.mathapp.progress.Storyteller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ApproachFeedbackActivity extends InjectedActivity {

    public static final float PERCENTAGE_HEIGHT = 0.38f;

    @InjectView(R.id.approach_list_mine) RecyclerView approachListMine;
    @InjectView(R.id.approach_list_correct) RecyclerView approachListCorrect;

    @Inject Storyteller storyteller;
    @Inject ApproachPartFeedbackAdapter approachFeedbackMineAdapter;
    @Inject ApproachPartFeedbackAdapter approachFeedbackCorrectAdapter;

    private List<ApproachPart> approachParts = Collections.emptyList();

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_approach_feedback);

        ButterKnife.inject(this);

        approachParts = storyteller.getCurrentApproach();

        initApproachListMine();
        initApproachListCorrect();
    }

    @Override public ActivityModule getModules() {
        return new ActivityModule(this);
    }

    private void initApproachListMine() {
        approachListMine.setAdapter(approachFeedbackMineAdapter);
        approachListMine.setLayoutManager(new LinearLayoutManager(this));

        approachFeedbackMineAdapter.setApproachParts(approachParts);

        // Set layout
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                MyApplication.screenWidth,
                (int) (MyApplication.screenHeight * PERCENTAGE_HEIGHT)
        );
        layoutParams.addRule(RelativeLayout.BELOW, R.id.chosen_title);
        approachListMine.setLayoutParams(layoutParams);
    }

    private void initApproachListCorrect() {
        approachListCorrect.setAdapter(approachFeedbackCorrectAdapter);
        approachListCorrect.setLayoutManager(new LinearLayoutManager(this));

        // Sort on approach position.
        ArrayList<ApproachPart> sortedApproachParts = new ArrayList<>();
        sortedApproachParts.addAll(approachParts);

        Collections.sort(sortedApproachParts, new Comparator<ApproachPart>() {
            public int compare(ApproachPart approachPart, ApproachPart approachPart2) {
                String pos1 = String.valueOf(approachPart.getPosition());
                String pos2 = String.valueOf(approachPart2.getPosition());
                return pos1.compareTo(pos2);
            }
        });

        approachFeedbackCorrectAdapter.setApproachParts(sortedApproachParts);

        // Set layout
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                MyApplication.screenWidth,
                (int) (MyApplication.screenHeight * PERCENTAGE_HEIGHT)
        );
        layoutParams.addRule(RelativeLayout.ABOVE, R.id.next_question_button);
        approachListCorrect.setLayoutParams(layoutParams);
    }

    @OnClick(R.id.next_question_button)
    public void onSubmitClicked() {
        storyteller.next();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_from_outside, R.anim.slide_right_to_outside);
    }

}

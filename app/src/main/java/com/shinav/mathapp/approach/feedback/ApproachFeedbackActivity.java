package com.shinav.mathapp.approach.feedback;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.R;
import com.shinav.mathapp.approach.Approach;
import com.shinav.mathapp.approach.ApproachAdapter;
import com.shinav.mathapp.progress.Storyteller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ApproachFeedbackActivity extends Activity {

    public static final float PERCENTAGE_HEIGHT = 0.38f;
    @InjectView(R.id.approach_list_mine) RecyclerView approachListMine;
    @InjectView(R.id.approach_list_correct) RecyclerView approachListCorrect;
    private List<Approach> approaches;
    private Storyteller storyteller;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_approach_feedback);

        ButterKnife.inject(this);

        storyteller = new Storyteller(this);
        approaches = storyteller.getCurrentApproach();

        initApproachListMine();
        initApproachListCorrect();
    }

    private void initApproachListMine() {
        ApproachAdapter approachAdapter = new ApproachFeedbackAdapter();

        approachListMine.setAdapter(approachAdapter);
        approachListMine.setLayoutManager(new LinearLayoutManager(this));

        approachAdapter.setApproaches(approaches);

        // Set layout
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                MyApplication.screenWidth,
                (int) (MyApplication.screenHeight * PERCENTAGE_HEIGHT)
        );
        layoutParams.addRule(RelativeLayout.BELOW, R.id.chosen_title);
        approachListMine.setLayoutParams(layoutParams);
    }

    private void initApproachListCorrect() {
        ApproachAdapter approachAdapter = new ApproachFeedbackAdapter();

        approachListCorrect.setAdapter(approachAdapter);
        approachListCorrect.setLayoutManager(new LinearLayoutManager(this));

        // Sort on approach position.
        ArrayList<Approach> sortedApproaches = new ArrayList<>();
        sortedApproaches.addAll(approaches);

        Collections.sort(sortedApproaches, new Comparator<Approach>() {
            public int compare(Approach approach, Approach approach2) {
                String pos1 = String.valueOf(approach.getPosition());
                String pos2 = String.valueOf(approach2.getPosition());
                return pos1.compareTo(pos2);
            }
        });

        approachAdapter.setApproaches(sortedApproaches);

        // Set layout
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                MyApplication.screenWidth,
                (int) (MyApplication.screenHeight * PERCENTAGE_HEIGHT)
        );
        layoutParams.addRule(RelativeLayout.ABOVE, R.id.submit_button);
        approachListCorrect.setLayoutParams(layoutParams);
    }

    @OnClick(R.id.submit_button)
    public void onSubmitClicked() {
        storyteller.next();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_from_outside, R.anim.slide_right_to_outside);
    }

}

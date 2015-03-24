package com.shinav.mathapp.approach;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.R;
import com.shinav.mathapp.drag.DragSortRecycler;
import com.shinav.mathapp.injection.InjectedActivity;
import com.shinav.mathapp.progress.Storyteller;
import com.shinav.mathapp.question.Question;
import com.shinav.mathapp.repository.RealmRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ApproachActivity extends InjectedActivity {

    @InjectView(R.id.approach_list) RecyclerView approachList;
    @InjectView(R.id.question_title) TextView questionTitle;
    @InjectView(R.id.question_text) TextView questionText;

    @Inject ApproachAdapter approachAdapter;
    @Inject Storyteller storyteller;
    @Inject RealmRepository realmRepository;

    private List<Approach> approach;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approach);
        ButterKnife.inject(this);

        String questionKey = getIntent().getStringExtra(Storyteller.TYPE_KEY);
        Question question = realmRepository.getQuestion(questionKey);

        approach = new ArrayList<>(question.getApproach());

        questionTitle.setText(question.getTitle());
        questionText.setText(question.getValue());

        initApproachList();
    }

    private void initApproachList() {
        approachList.setAdapter(approachAdapter);
        approachList.setLayoutManager(new LinearLayoutManager(this));
        approachList.setItemAnimator(null);

        loadApproach();
        setupDragArea();
    }

    private void loadApproach() {
        Collections.shuffle(approach);
        approachAdapter.setApproaches(approach);
    }

    private void setupDragArea() {
        DragSortRecycler dragSortRecycler = new DragSortRecycler();
        dragSortRecycler.setViewHandleId(R.id.approach_list_item);
        dragSortRecycler.setFloatingBgColor(Color.parseColor("#ffffff"));

        dragSortRecycler.setOnItemMovedListener(new DragSortRecycler.OnItemMovedListener() {
            @Override public void onItemMoved(int from, int to) {
                approach.add(to, approach.remove(from));
                approachAdapter.setApproaches(approach);
            }
        });

        approachList.addItemDecoration(dragSortRecycler);
        approachList.addOnItemTouchListener(dragSortRecycler);
        approachList.setOnScrollListener(dragSortRecycler.getScrollListener());

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                MyApplication.screenWidth,
                (int) (MyApplication.screenHeight * 0.41f)
        );
        layoutParams.addRule(RelativeLayout.ABOVE, R.id.next_question_button);
        approachList.setLayoutParams(layoutParams);
    }

    @OnClick(R.id.next_question_button)
    public void onSubmitClicked() {
        storyteller.setCurrentApproach(approach);
        storyteller.next();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_from_outside, R.anim.slide_right_to_outside);
    }

}

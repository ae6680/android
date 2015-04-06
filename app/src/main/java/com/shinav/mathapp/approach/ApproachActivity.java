package com.shinav.mathapp.approach;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.R;
import com.shinav.mathapp.db.mapper.ApproachMapper;
import com.shinav.mathapp.db.mapper.ApproachPartMapper;
import com.shinav.mathapp.db.mapper.QuestionMapper;
import com.shinav.mathapp.db.pojo.Approach;
import com.shinav.mathapp.db.pojo.ApproachPart;
import com.shinav.mathapp.db.pojo.Question;
import com.shinav.mathapp.injection.component.ApproachActivityComponent;
import com.shinav.mathapp.progress.Storyteller;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

public class ApproachActivity extends ActionBarActivity {

    @InjectView(R.id.approach_part_list) ApproachDragRecyclerView approachPartList;
    @InjectView(R.id.question_text) TextView questionText;
    @InjectView(R.id.toolbar) Toolbar toolbar;

    @Inject Storyteller storyteller;

    @Inject QuestionMapper questionMapper;
    @Inject ApproachMapper approachMapper;
    @Inject ApproachPartMapper approachPartMapper;

    private List<ApproachPart> approachParts = Collections.emptyList();

    private Subscription questionSubscription;
    private Subscription approachSubscription;
    private Subscription approachPartSubscription;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approach);

        ButterKnife.inject(this);
        ApproachActivityComponent component = ApproachActivityComponent.Initializer.init(
                this, ((MyApplication) getApplication()).isMockMode());
        component.inject(this);
    }

    @Override protected void onResume() {
        super.onResume();

        final String questionKey = getIntent().getStringExtra(Storyteller.TYPE_KEY);

        questionSubscription = questionMapper.getByKey(
                questionKey, new Action1<Question>() {

                    @Override public void call(Question question) {
                        questionText.setText(question.getValue());
                        initToolbar(question.getTitle());

                        approachSubscription = approachMapper.getApproachByQuestionKey(
                                questionKey, new Action1<Approach>() {

                                    @Override public void call(Approach approach) {

                                        approachPartSubscription = approachPartMapper.getApproachPartsByApproachKey(
                                                approach.getKey(), new Action1<List<ApproachPart>>() {

                                                    @Override
                                                    public void call(List<ApproachPart> approachParts) {
                                                        approachPartList.setApproachParts(approachParts);
                                                    }

                                                });

                                    }
                                });

                    }
                });
    }

    @Override protected void onPause() {
        super.onPause();
        questionSubscription.unsubscribe();
        approachSubscription.unsubscribe();
        approachPartSubscription.unsubscribe();
    }

    private void initToolbar(String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @OnClick(R.id.next_question_button)
    public void onSubmitClicked() {
        storyteller.setCurrentApproach(approachParts);
        storyteller.next();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_from_outside, R.anim.slide_right_to_outside);
    }

}

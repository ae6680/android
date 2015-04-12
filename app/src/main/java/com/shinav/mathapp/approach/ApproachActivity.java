package com.shinav.mathapp.approach;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.R;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.Approach;
import com.shinav.mathapp.db.pojo.ApproachPart;
import com.shinav.mathapp.db.pojo.Question;
import com.shinav.mathapp.db.repository.ApproachPartRepository;
import com.shinav.mathapp.db.repository.ApproachRepository;
import com.shinav.mathapp.db.repository.QuestionRepository;
import com.shinav.mathapp.injection.component.ApproachActivityComponent;
import com.shinav.mathapp.storytelling.StorytellingService;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

public class ApproachActivity extends ActionBarActivity {

    @InjectView(R.id.approach_part_list) ApproachDragRecyclerView approachPartList;
    @InjectView(R.id.question_text) TextView questionText;
    @InjectView(R.id.toolbar) Toolbar toolbar;

    @Inject QuestionRepository questionRepository;
    @Inject ApproachRepository approachRepository;
    @Inject ApproachPartRepository approachPartRepository;

//    private List<ApproachPart> approachParts = Collections.emptyList();

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approach);

        ButterKnife.inject(this);
        inject();

        final String questionKey = getIntent().getStringExtra(Tables.StoryboardFrame.FRAME_TYPE_KEY);

        loadQuestion(questionKey);
        loadApproach(questionKey);
    }

    public void inject() {
        ApproachActivityComponent component = ApproachActivityComponent.Initializer.init(
                this, ((MyApplication) getApplication()).isMockMode());
        component.inject(this);
    }

    private void loadQuestion(String questionKey) {
        Observable<Question> questionObservable = questionRepository.
                getByKey(questionKey).first();

        questionObservable.subscribe(new Action1<Question>() {
            @Override public void call(Question question) {
                questionText.setText(question.getValue());
                initToolbar(question.getTitle());
            }
        });
    }

    private void loadApproach(String questionKey) {
        Observable<Approach> approachObservable = approachRepository.
                getApproachByQuestionKey(questionKey).first();

        approachObservable.subscribe(new Action1<Approach>() {
            @Override public void call(Approach approach) {

                Observable<List<ApproachPart>> approachListObservable = approachPartRepository.
                        getApproachPartsByApproachKey(approach.getKey()).first();

                approachListObservable.subscribe(new Action1<List<ApproachPart>>() {
                    @Override public void call(List<ApproachPart> approachParts) {
                        approachPartList.setApproachParts(approachParts);
                    }
                });

            }
        });
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
//        storyteller.setCurrentApproach(approachParts);
        next();
    }

    public void next() {
        Intent intent = new Intent(this, StorytellingService.class);

        intent.setAction(StorytellingService.ACTION_NEXT);

        startService(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_from_outside, R.anim.slide_right_to_outside);
    }

}

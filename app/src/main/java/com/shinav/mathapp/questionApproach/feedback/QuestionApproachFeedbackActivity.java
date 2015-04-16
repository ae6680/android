package com.shinav.mathapp.questionApproach.feedback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.R;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.GivenApproach;
import com.shinav.mathapp.db.pojo.Question;
import com.shinav.mathapp.db.pojo.QuestionApproach;
import com.shinav.mathapp.db.pojo.QuestionApproachPart;
import com.shinav.mathapp.db.repository.GivenApproachRepository;
import com.shinav.mathapp.db.repository.QuestionApproachPartRepository;
import com.shinav.mathapp.db.repository.QuestionApproachRepository;
import com.shinav.mathapp.db.repository.QuestionRepository;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.storytelling.StorytellingService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.functions.Action1;

public class QuestionApproachFeedbackActivity extends Activity {

    public static final float PERCENTAGE_HEIGHT = 0.38f;

    @InjectView(R.id.approach_list_mine) RecyclerView approachListMine;
    @InjectView(R.id.approach_list_correct) RecyclerView approachListCorrect;
    @InjectView(R.id.background_view) ImageView backgroundView;

    @Inject QuestionApproachPartFeedbackAdapter approachFeedbackMineAdapter;
    @Inject QuestionApproachPartFeedbackAdapter approachFeedbackCorrectAdapter;

    @Inject QuestionRepository questionRepository;
    @Inject QuestionApproachRepository questionApproachRepository;
    @Inject QuestionApproachPartRepository questionApproachPartRepository;
    @Inject GivenApproachRepository givenApproachRepository;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_approach_feedback);

        ButterKnife.inject(this);
        inject();

        final String questionKey = getIntent().
                getStringExtra(Tables.StoryboardFrame.FRAME_TYPE_KEY);

        loadQuestion(questionKey);
        loadApproach(questionKey);
    }

    public void inject() {
        ComponentFactory.getActivityComponent(this).inject(this);
    }

    private void loadQuestion(String questionKey) {
        questionRepository.get(questionKey, new Action1<Question>() {

            @Override public void call(Question question) {
//                loadBackground(question.getBackgroundImageUrl());
                loadBackground("http://i.imgur.com/JfDNNOy.png");
            }
        });
    }

    private void loadBackground(String imageUrl) {
        Picasso.with(this)
                .load(imageUrl)
                .centerCrop()
                .fit()
                .into(backgroundView);
        backgroundView.setImageAlpha(50);
    }

    private void loadApproach(String questionKey) {
        questionApproachRepository.getApproach(questionKey, new Action1<QuestionApproach>() {

            @Override public void call(QuestionApproach questionApproach) {
                loadApproachParts(questionApproach.getKey());
            }
        });
    }

    private void loadApproachParts(final String approachKey) {
        questionApproachPartRepository.getApproachParts(approachKey, new Action1<List<QuestionApproachPart>>() {

            @Override public void call(List<QuestionApproachPart> questionApproachParts) {
                loadGivenApproach(approachKey, questionApproachParts);
                initApproachListCorrect(questionApproachParts);
            }
        });
    }

    private void loadGivenApproach(String approachKey, final List<QuestionApproachPart> questionApproachParts) {
        givenApproachRepository.get(approachKey, new Action1<GivenApproach>() {
            @Override public void call(GivenApproach givenApproach) {
                List<QuestionApproachPart> arrangedQuestionApproachParts =
                        sortOnGivenApproachArrangement(questionApproachParts, givenApproach);

                initApproachListMine(arrangedQuestionApproachParts);
            }
        });
    }

    private List<QuestionApproachPart> sortOnGivenApproachArrangement(final List<QuestionApproachPart> questionApproachParts, GivenApproach givenApproach) {

        final String[] arrangement = TextUtils.split(givenApproach.getArrangement(), ",");

        List<QuestionApproachPart> arrangedApproaches = new ArrayList<>();

        for (int i = 0; i < arrangement.length; i++) {
            int index = Integer.parseInt(arrangement[i]);

            // Don't go out of bounds if amount changed since last attempt.
            if (index < questionApproachParts.size()) {
                arrangedApproaches.add(questionApproachParts.get(index));
            }
        }

        return arrangedApproaches;
    }

    private void initApproachListMine(List<QuestionApproachPart> questionApproachParts) {

        approachFeedbackMineAdapter.setQuestionApproachParts(questionApproachParts);

        RelativeLayout.LayoutParams layoutParams = getLayoutParams();
        layoutParams.addRule(RelativeLayout.BELOW, R.id.chosen_title);

        setupApproachList(approachListMine, approachFeedbackMineAdapter, layoutParams);
    }

    private void initApproachListCorrect(List<QuestionApproachPart> questionApproachParts) {
        Collections.sort(questionApproachParts);

        approachFeedbackCorrectAdapter.setQuestionApproachParts(questionApproachParts);

        RelativeLayout.LayoutParams layoutParams = getLayoutParams();
        layoutParams.addRule(RelativeLayout.ABOVE, R.id.next_question_button);

        setupApproachList(approachListCorrect, approachFeedbackCorrectAdapter, layoutParams);
    }

    private RelativeLayout.LayoutParams getLayoutParams() {
        return new RelativeLayout.LayoutParams(
                    MyApplication.screenWidth,
                    (int) (MyApplication.screenHeight * PERCENTAGE_HEIGHT)
            );
    }

    private void setupApproachList(
            RecyclerView approachList,
            QuestionApproachPartFeedbackAdapter approachPartAdapter,
            RelativeLayout.LayoutParams layoutParams
    ) {
        approachList.setAdapter(approachPartAdapter);
        approachList.setLayoutManager(new LinearLayoutManager(this));
        approachList.setLayoutParams(layoutParams);
    }

    @OnClick(R.id.next_question_button)
    public void onSubmitClicked() {
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

package com.shinav.mathapp.approach;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.R;
import com.shinav.mathapp.db.dataMapper.GivenApproachMapper;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.Approach;
import com.shinav.mathapp.db.pojo.ApproachPart;
import com.shinav.mathapp.db.pojo.GivenApproach;
import com.shinav.mathapp.db.pojo.Question;
import com.shinav.mathapp.db.repository.ApproachPartRepository;
import com.shinav.mathapp.db.repository.ApproachRepository;
import com.shinav.mathapp.db.repository.QuestionRepository;
import com.shinav.mathapp.injection.component.ApproachActivityComponent;
import com.shinav.mathapp.storytelling.StorytellingService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.functions.Action1;

public class ApproachActivity extends ActionBarActivity {

    @InjectView(R.id.approach_part_list) ApproachDragRecyclerView approachPartList;
    @InjectView(R.id.question_text) TextView questionText;
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.background_view) ImageView backgroundView;

    @Inject QuestionRepository questionRepository;
    @Inject ApproachRepository approachRepository;
    @Inject ApproachPartRepository approachPartRepository;

    @Inject GivenApproachMapper givenApproachMapper;

    private Approach approach;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approach);

        ButterKnife.inject(this);
        inject();

        final String questionKey = getIntent().
                getStringExtra(Tables.StoryboardFrame.FRAME_TYPE_KEY);

        loadQuestion(questionKey);
        loadApproach(questionKey);
    }

    public void inject() {
        ApproachActivityComponent component = ApproachActivityComponent.Initializer.init(
                this, ((MyApplication) getApplication()).isMockMode());
        component.inject(this);
    }

    private void loadQuestion(String questionKey) {
        questionRepository.get(questionKey, new Action1<Question>() {

            @Override public void call(Question question) {
                questionText.setText(question.getValue());
                initToolbar(question.getTitle());
//                loadBackground(question.getBackgroundImageUrl());
                loadBackground("http://i.imgur.com/JfDNNOy.png");
            }
        });
    }

    private void loadApproach(String questionKey) {
        approachRepository.getApproach(questionKey, new Action1<Approach>() {

            @Override public void call(Approach approach) {
                ApproachActivity.this.approach = approach;
                loadApproachParts(approach.getKey());
            }
        });
    }

    private void loadApproachParts(String approachKey) {
        approachPartRepository.getApproachParts(approachKey, new Action1<List<ApproachPart>>() {

            @Override public void call(List<ApproachPart> approachParts) {
                approachPartList.setApproachParts(approachParts);
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

    private void loadBackground(String imageUrl) {
        Picasso.with(this)
                .load(imageUrl)
                .centerCrop()
                .fit()
                .into(backgroundView);
        backgroundView.setImageAlpha(50);
    }

    @OnClick(R.id.next_question_button)
    public void onSubmitClicked() {
        saveGivenApproach();
        next();
    }

    private void saveGivenApproach() {
        List<ApproachPart> approachParts =
                ((ApproachPartAdapter) approachPartList.getAdapter()).getApproachParts();

        String order = getOrder(approachParts);

        GivenApproach givenApproach = new GivenApproach();
        givenApproach.setApproachKey(approach.getKey());
        givenApproach.setArrangement(order);

        givenApproachMapper.insert(givenApproach);
    }

    private String getOrder(List<ApproachPart> approachParts) {
        List<Integer> positions = new ArrayList<>();
        for (ApproachPart approachPart : approachParts) {
            positions.add(approachPart.getPosition());
        }

        return TextUtils.join(",", positions);
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

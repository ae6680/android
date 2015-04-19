package com.shinav.mathapp.questionApproach;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shinav.mathapp.R;
import com.shinav.mathapp.card.Card;
import com.shinav.mathapp.card.CardViewPager;
import com.shinav.mathapp.db.dataMapper.GivenQuestionApproachMapper;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.GivenQuestionApproach;
import com.shinav.mathapp.db.pojo.Question;
import com.shinav.mathapp.db.pojo.QuestionApproach;
import com.shinav.mathapp.db.pojo.QuestionApproachPart;
import com.shinav.mathapp.db.repository.QuestionApproachPartRepository;
import com.shinav.mathapp.db.repository.QuestionApproachRepository;
import com.shinav.mathapp.db.repository.QuestionRepository;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.question.card.QuestionAnnexCardView;
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

public class QuestionApproachActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.background_view) ImageView backgroundView;
    @InjectView(R.id.card_view_pager) CardViewPager cardViewPager;
    @InjectView(R.id.view_pager_indicator_container) LinearLayout viewPagerIndicator;
    @InjectView(R.id.approach_part_list) QuestionApproachDragRecyclerView approachPartList;

    @Inject QuestionRepository questionRepository;
    @Inject QuestionApproachRepository questionApproachRepository;
    @Inject QuestionApproachPartRepository questionApproachPartRepository;

    @Inject QuestionSimpleCardView questionSimpleCardView;

    @Inject GivenQuestionApproachMapper givenQuestionApproachMapper;

    private QuestionApproach questionApproach;

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
//        ApproachActivityComponent component = ApproachActivityComponent.Initializer.init(
//                this, ((MyApplication) getApplication()).isMockMode());
//        component.inject(this);
        ComponentFactory.getActivityComponent(this).inject(this);
    }

    private void loadQuestion(String questionKey) {
        questionRepository.get(questionKey, new Action1<Question>() {

            @Override public void call(Question question) {

                initToolbar(question.getTitle());
//                loadBackground(question.getBackgroundImageUrl());
                loadBackground("http://i.imgur.com/JfDNNOy.png");

                initViewPager(question);
            }
        });
    }

    private void loadApproach(String questionKey) {
        questionApproachRepository.get(questionKey, new Action1<QuestionApproach>() {

            @Override public void call(QuestionApproach questionApproach) {
                QuestionApproachActivity.this.questionApproach = questionApproach;
                loadApproachParts(questionApproach.getKey());
            }
        });
    }

    private void loadApproachParts(String approachKey) {
        questionApproachPartRepository.getApproachParts(approachKey, new Action1<List<QuestionApproachPart>>() {

            @Override public void call(List<QuestionApproachPart> questionApproachParts) {
                Collections.shuffle(questionApproachParts);
                approachPartList.setQuestionApproachParts(questionApproachParts);
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

    private void initViewPager(Question question) {
        List<Card> cards = new ArrayList<>();

        questionSimpleCardView.setQuestionValue(question.getValue());
        cards.add(questionSimpleCardView);

        if (!TextUtils.isEmpty(question.getAnnexImageUrl())) {
            QuestionAnnexCardView questionAnnexCardView =
                    new QuestionAnnexCardView(this);

            questionAnnexCardView.setAnnexImageUrl(question.getAnnexImageUrl());
            cards.add(questionAnnexCardView);
        }

        cardViewPager.setIndicator(viewPagerIndicator);
        cardViewPager.setCards(cards);
        cardViewPager.setCurrentItem(0);
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
        List<QuestionApproachPart> questionApproachParts =
                ((QuestionApproachPartAdapter) approachPartList.getAdapter()).getQuestionApproachParts();

        String order = getOrder(questionApproachParts);

        GivenQuestionApproach givenQuestionApproach = new GivenQuestionApproach();
        givenQuestionApproach.setApproachKey(questionApproach.getKey());
        givenQuestionApproach.setArrangement(order);

        givenQuestionApproachMapper.insert(givenQuestionApproach);
    }

    private String getOrder(List<QuestionApproachPart> questionApproachParts) {
        List<Integer> positions = new ArrayList<>();
        for (QuestionApproachPart questionApproachPart : questionApproachParts) {
            positions.add(questionApproachPart.getPosition());
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

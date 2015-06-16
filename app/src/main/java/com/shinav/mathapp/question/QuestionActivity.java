package com.shinav.mathapp.question;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.R;
import com.shinav.mathapp.animation.SimpleAnimatorListener;
import com.shinav.mathapp.animation.YAnimation;
import com.shinav.mathapp.calculator.CalculatorFragment;
import com.shinav.mathapp.card.Card;
import com.shinav.mathapp.card.CardViewPager;
import com.shinav.mathapp.db.dataMapper.GivenAnswerDataMapper;
import com.shinav.mathapp.db.dataMapper.QuestionDataMapper;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.GivenAnswer;
import com.shinav.mathapp.db.pojo.Question;
import com.shinav.mathapp.db.pojo.QuestionApproach;
import com.shinav.mathapp.db.pojo.QuestionApproachPart;
import com.shinav.mathapp.db.pojo.QuestionExplanation;
import com.shinav.mathapp.db.repository.QuestionApproachPartRepository;
import com.shinav.mathapp.db.repository.QuestionApproachRepository;
import com.shinav.mathapp.db.repository.QuestionExplanationRepository;
import com.shinav.mathapp.db.repository.QuestionRepository;
import com.shinav.mathapp.event.AnswerFieldClickedEvent;
import com.shinav.mathapp.event.AnswerSubmittedEvent;
import com.shinav.mathapp.event.CalculatorResultAreaClickedEvent;
import com.shinav.mathapp.event.NextQuestionClickedEvent;
import com.shinav.mathapp.event.NumpadOperationClickedEvent;
import com.shinav.mathapp.image.BackgroundLoader;
import com.shinav.mathapp.injection.component.Injector;
import com.shinav.mathapp.question.card.QuestionAnnexCardView;
import com.shinav.mathapp.question.card.QuestionAnswerCardView;
import com.shinav.mathapp.question.card.QuestionApproachCardView;
import com.shinav.mathapp.question.card.QuestionCardView;
import com.shinav.mathapp.question.card.QuestionExplanationCardView;
import com.shinav.mathapp.question.card.QuestionNextCardView;
import com.shinav.mathapp.storytelling.StorytellingService;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.functions.Action1;

public class QuestionActivity extends ActionBarActivity {

    public static final String CALCULATOR_FRAGMENT = "CalculatorFragment";

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.activity_container) RelativeLayout activityContainer;
    @InjectView(R.id.card_view_pager) CardViewPager cardViewPager;
    @InjectView(R.id.view_pager_indicator_container) LinearLayout viewPagerIndicator;
    @InjectView(R.id.calculator_container) RelativeLayout calculatorContainer;
    @InjectView(R.id.background_view) ImageView backgroundView;

    @Inject Bus bus;

    @Inject QuestionApproachCardView questionApproachCardView;
    @Inject QuestionCardView questionCardView;
    @Inject QuestionNextCardView questionNextCardView;

    @Inject QuestionRepository questionRepository;
    @Inject QuestionApproachRepository questionApproachRepository;
    @Inject QuestionApproachPartRepository questionApproachPartRepository;
    @Inject QuestionExplanationRepository questionExplanationRepository;

    @Inject QuestionDataMapper questionDataMapper;
    @Inject GivenAnswerDataMapper givenAnswerDataMapper;
    @Inject BackgroundLoader backgroundLoader;

    private Question question;
    private int moveToIndex = 0;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ButterKnife.inject(this);
        inject();

        final String questionKey =
                getIntent().getStringExtra(Tables.StoryboardFrame.FRAME_TYPE_KEY);

        loadQuestion(questionKey);
        loadApproach(questionKey);

        initCalculator();
    }

    @Override public void onStart() {
        super.onStart();
        registerBus();
    }

    @Override public void onStop() {
        super.onStop();
        unregisterBus();
    }

    @Override protected void onPause() {
        super.onPause();
        onCalculatorResultAreaClicked(null);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.question_menu, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_approach:
                cardViewPager.setCurrentItem(0);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_from_outside, R.anim.slide_right_to_outside);
    }

    public void inject() {
        Injector.getActivityComponent(this).inject(this);
    }

    public void registerBus() {
        bus.register(this);
    }

    public void unregisterBus() {
        bus.unregister(this);
    }

    private void loadQuestion(String questionKey) {
        questionRepository.find(questionKey, new Action1<Question>() {

            @Override public void call(Question question) {
                QuestionActivity.this.question = question;
                initToolbar(question.getTitle());

                backgroundLoader.loadBackground(
                    backgroundView,
                    question.getBackgroundImageUrl()
                );
            }
        });
    }

    private void loadApproach(String questionKey) {
        questionApproachRepository.findByParent(questionKey, new Action1<QuestionApproach>() {

            @Override public void call(QuestionApproach questionApproach) {
                loadApproachParts(questionApproach.getKey());
            }
        });
    }

    private void loadApproachParts(String approachKey) {
        questionApproachPartRepository.findAllByParent(approachKey, new Action1<List<QuestionApproachPart>>() {

            @Override public void call(List<QuestionApproachPart> questionApproachParts) {
                initViewPager(questionApproachParts);
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

    private void initViewPager(List<QuestionApproachPart> questionApproachParts) {
        List<Card> cards = new ArrayList<>();

        questionApproachCardView.setApproachParts(questionApproachParts);
        cards.add(questionApproachCardView);

        questionCardView.setQuestionValue(question.getValue());
        cards.add(questionCardView);

        if (!TextUtils.isEmpty(question.getAnnexImageUrl())) {
            QuestionAnnexCardView questionAnnexCardView =
                    new QuestionAnnexCardView(this);

            questionAnnexCardView.setAnnexImageUrl(question.getAnnexImageUrl());
            cards.add(questionAnnexCardView);
        }

        cardViewPager.setIndicator(viewPagerIndicator);
        cardViewPager.setCards(cards);
        cardViewPager.setCurrentItem(1);
    }

    private void initCalculator() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.calculator_container, new CalculatorFragment(), CALCULATOR_FRAGMENT).commit();
    }

    @Subscribe public void OnAnswerSubmittedEvent(AnswerSubmittedEvent event) {
        startAnimation(event.getAnswer());

        questionCardView.setAnswerFieldEnabled(false);
        questionCardView.setSubmitButtonEnabled(false);

        saveGivenAnswer(event.getAnswer());

        if (!isAlreadyMade()) {
            openNextQuestion();
        }

        updateProgressState(event.getAnswer());
    }

    private boolean isAlreadyMade() {
        int state = question.getProgressState();
        return state == Question.STATE_PASSED || state == Question.STATE_FAILED;
    }

    private void openNextQuestion() {
        Intent intent = new Intent(this, StorytellingService.class);

        intent.setAction(StorytellingService.ACTION_OPEN_NEXT_QUESTION_FROM);
        intent.putExtra(StorytellingService.EXTRA_FRAME_TYPE_KEY, question.getKey());

        startService(intent);
    }

    private void saveGivenAnswer(String answer) {
        GivenAnswer givenAnswer = new GivenAnswer();

        givenAnswer.setQuestionKey(question.getKey());
        givenAnswer.setValue(answer);
        givenAnswer.setGivenAt((int) System.currentTimeMillis());

        givenAnswerDataMapper.insert(givenAnswer);
    }

    private void updateProgressState(String answer) {
        if (answer.equals(question.getAnswer())) {
            question.setProgressState(Question.STATE_PASSED);
        } else {
            question.setProgressState(Question.STATE_FAILED);
        }

        questionDataMapper.update(question, question.getKey());
    }

    @Subscribe public void onNextButtonClicked(NextQuestionClickedEvent event) {
        Intent intent = new Intent(this, StorytellingService.class);

        intent.setAction(StorytellingService.ACTION_START_NEXT_FROM);
        intent.putExtra(StorytellingService.EXTRA_FRAME_TYPE_KEY, question.getKey());

        startService(intent);
    }

    @Subscribe public void onAnswerFieldClicked(AnswerFieldClickedEvent event) {
        CalculatorFragment fragment = (CalculatorFragment) getSupportFragmentManager()
                .findFragmentByTag(CALCULATOR_FRAGMENT);
        fragment.releaseFocus();
    }

    @Subscribe public void onCalculatorResultAreaClicked(CalculatorResultAreaClickedEvent event) {
        questionCardView.releaseFocus();

        CalculatorFragment fragment = (CalculatorFragment) getSupportFragmentManager()
                .findFragmentByTag(CALCULATOR_FRAGMENT);
        fragment.gainFocus();
    }

    @Subscribe public void onCalculatorNumpadClicked(NumpadOperationClickedEvent event) {
        questionCardView.onCalculatorNumpadClicked(event);
    }

    private void startAnimation(String givenAnswer) {
        float originalScaleX = viewPagerIndicator.getScaleX();
        float originalScaleY = viewPagerIndicator.getScaleY();
        float questionResultY = MyApplication.screenHeight * 0.62f;
        float questionNextY = MyApplication.screenHeight * 0.84f;

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(viewPagerIndicator,
                "scaleX", originalScaleX, 0f);

        anim1.addListener(new SimpleAnimatorListener() {
            @Override public void onAnimationEnd(Animator animation) {

                questionExplanationRepository.findAllByParent(question.getKey(), new Action1<List<QuestionExplanation>>() {
                    @Override public void call(List<QuestionExplanation> questionExplanations) {
                        List<Card> cards = new ArrayList<>();

                        for (QuestionExplanation questionExplanation : questionExplanations) {

                            QuestionExplanationCardView explanationView =
                                new QuestionExplanationCardView(QuestionActivity.this);
                            explanationView.setExplanationText(questionExplanation.getText());
                            explanationView.setExplanationImageUrl(questionExplanation.getImageUrl());

                            cards.add(explanationView);
                        }

                        moveToIndex = cardViewPager.getChildCount();
                        cardViewPager.addCards(cards);
                    }
                });

            }
        });

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(viewPagerIndicator,
                "scaleY", originalScaleY, 0f);

        ObjectAnimator anim3 = YAnimation.create(calculatorContainer, 1000);

        final QuestionAnswerCardView questionAnswerCard = new QuestionAnswerCardView(this);
        questionAnswerCard.setQuestion(question);
        questionAnswerCard.giveAnswer(givenAnswer);
        questionAnswerCard.setVisibility(View.GONE);

        activityContainer.addView(questionAnswerCard);

        ObjectAnimator anim4 = ObjectAnimator.ofFloat(questionAnswerCard,
                "Y", 3000, questionResultY);

        anim4.addListener(new SimpleAnimatorListener() {
            @Override public void onAnimationStart(Animator animation) {
                questionAnswerCard.setVisibility(View.VISIBLE);
            }
        });

        questionNextCardView.setVisibility(View.GONE);
        activityContainer.addView(questionNextCardView);

        ObjectAnimator anim5 = ObjectAnimator.ofFloat(questionNextCardView,
                "Y", 3000, questionNextY);

        anim5.addListener(new SimpleAnimatorListener() {
            @Override public void onAnimationStart(Animator animation) {
                questionNextCardView.setVisibility(View.VISIBLE);
            }
        });

        ObjectAnimator anim6 = ObjectAnimator.ofFloat(viewPagerIndicator,
                "scaleX", 0f, originalScaleX);

        ObjectAnimator anim7 = ObjectAnimator.ofFloat(viewPagerIndicator,
                "scaleY", 0f, originalScaleY);

        anim7.addListener(new SimpleAnimatorListener() {
            @Override public void onAnimationEnd(Animator animation) {
                cardViewPager.setCurrentItem(moveToIndex);
            }
        });

        AnimatorSet set = new AnimatorSet();

        set.play(anim1).with(anim2);
        set.play(anim3).after(100);
        set.play(anim4).after(600);
        set.play(anim5).after(1000);
        set.play(anim6).with(anim7).after(1500);

        set.start();
    }

}

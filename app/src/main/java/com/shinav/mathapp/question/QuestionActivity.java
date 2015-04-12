package com.shinav.mathapp.question;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.R;
import com.shinav.mathapp.animation.SimpleAnimatorListener;
import com.shinav.mathapp.animation.YAnimation;
import com.shinav.mathapp.calculator.CalculatorFragment;
import com.shinav.mathapp.card.Card;
import com.shinav.mathapp.card.CardViewPager;
import com.shinav.mathapp.db.dataMapper.ApproachMapper;
import com.shinav.mathapp.db.dataMapper.ApproachPartMapper;
import com.shinav.mathapp.db.dataMapper.QuestionMapper;
import com.shinav.mathapp.db.dataMapper.StoryProgressPartMapper;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.Approach;
import com.shinav.mathapp.db.pojo.ApproachPart;
import com.shinav.mathapp.db.pojo.Question;
import com.shinav.mathapp.db.pojo.StoryProgressPart;
import com.shinav.mathapp.db.repository.ApproachPartRepository;
import com.shinav.mathapp.db.repository.ApproachRepository;
import com.shinav.mathapp.db.repository.QuestionRepository;
import com.shinav.mathapp.db.repository.StoryProgressPartRepository;
import com.shinav.mathapp.event.OnAnswerSubmittedEvent;
import com.shinav.mathapp.event.OnCalculatorResultAreaClickedEvent;
import com.shinav.mathapp.event.OnNextQuestionClickedEvent;
import com.shinav.mathapp.event.OnNumpadOperationClickedEvent;
import com.shinav.mathapp.event.SendNextQuestionKey;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.question.card.QuestionAnswerCardView;
import com.shinav.mathapp.question.card.QuestionApproachCardView;
import com.shinav.mathapp.question.card.QuestionCardView;
import com.shinav.mathapp.question.card.QuestionExplanationView;
import com.shinav.mathapp.question.card.QuestionNextCardView;
import com.shinav.mathapp.question.event.OnAnswerFieldClickedEvent;
import com.shinav.mathapp.storytelling.StorytellingService;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.functions.Action1;

public class QuestionActivity extends ActionBarActivity {

    public static final String CALCULATOR_FRAGMENT = "CalculatorFragment";

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.activity_container) RelativeLayout activityContainer;
    @InjectView(R.id.card_view_pager) CardViewPager cardViewPager;
    @InjectView(R.id.view_pager_indicator_container) LinearLayout viewPagerIndicator;
    @InjectView(R.id.calculator_container) RelativeLayout calculatorContainer;

    @Inject Bus bus;

    @Inject SqlBrite db;
    @Inject QuestionMapper questionMapper;
    @Inject ApproachMapper approachMapper;
    @Inject ApproachPartMapper approachPartMapper;
    @Inject StoryProgressPartMapper storyProgressPartMapper;

    @Inject QuestionApproachCardView questionApproachCardView;
    @Inject QuestionCardView questionCardView;
    @Inject QuestionNextCardView questionNextCardView;

    @Inject StoryProgressPartRepository storyProgressPartRepository;
    @Inject QuestionRepository questionRepository;
    @Inject ApproachRepository approachRepository;
    @Inject ApproachPartRepository approachPartRepository;

    private Question question;
    private String storyProgressKey;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ButterKnife.inject(this);
        inject();

        final String questionKey = getIntent().getStringExtra(Tables.StoryboardFrame.FRAME_TYPE_KEY);

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
        ComponentFactory.getActivityComponent(this).inject(this);
    }

    public void registerBus() {
        bus.register(this);
    }

    public void unregisterBus() {
        bus.unregister(this);
    }

    private void loadQuestion(String questionKey) {
        Observable<Question> questionObservable = questionRepository.
                getByKey(questionKey).first();

        questionObservable.subscribe(new Action1<Question>() {
            @Override public void call(Question question) {
                QuestionActivity.this.question = question;
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
                        initViewPager(approachParts);
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

    private void initViewPager(List<ApproachPart> approachParts) {
        List<Card> cards = new ArrayList<>();

        questionApproachCardView.setApproachParts(approachParts);
        cards.add(questionApproachCardView);

        questionCardView.setQuestionValue(question.getValue());
        cards.add(questionCardView);

        cardViewPager.setIndicator(viewPagerIndicator);
        cardViewPager.setCards(cards);
        cardViewPager.setCurrentItem(1);
    }

    private void initCalculator() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.calculator_container, new CalculatorFragment(), CALCULATOR_FRAGMENT).commit();
    }

    @Subscribe public void OnAnswerSubmittedEvent(OnAnswerSubmittedEvent event) {
        startAnimation(event.getAnswer());

        questionCardView.setAnswerFieldEnabled(false);
        questionCardView.setSubmitButtonEnabled(false);

//        updateStoryProgress(event);
    }

    private void updateStoryProgress(final OnAnswerSubmittedEvent event) {

        Observable<StoryProgressPart> storyProgressPartObservable =
                storyProgressPartRepository.getByQuestionKey(question.getKey()).first();

        storyProgressPartObservable.subscribe(new Action1<StoryProgressPart>() {

            @Override public void call(StoryProgressPart storyProgressPart) {
                storyProgressPart.setState(isCorrect(question, event.getAnswer()));
                storyProgressPartMapper.update(storyProgressPart);

                storyProgressKey = storyProgressPart.getStoryProgressKey();
                createNextStoryProgressPart();
            }
        });
    }

    private void createNextStoryProgressPart() {
        requestNextQuestionKey();
    }

    private void requestNextQuestionKey() {
        Intent intent = new Intent(this, StorytellingService.class);

        intent.setAction(StorytellingService.ACTION_NEXT_KEY);

        startService(intent);
    }

    @Subscribe void onSendNextQuestionKey(SendNextQuestionKey event) {

        final String nextQuestionKey = event.getNextQuestionKey();

        if (!TextUtils.isEmpty(nextQuestionKey)) {

            Observable<StoryProgressPart> storyProgressPartObservable =
                    storyProgressPartRepository.getByQuestionKey(nextQuestionKey).first();

            storyProgressPartObservable.subscribe(new Action1<StoryProgressPart>() {
                @Override public void call(StoryProgressPart storyProgressPart) {
                    if (storyProgressPart == null) {

                        StoryProgressPart newStoryProgressPart = new StoryProgressPart();

                        Cursor c = db.query(
                                "SELECT * FROM " + Tables.Question.TABLE_NAME +
                                        " WHERE " + Tables.Question.KEY + " = ?"
                                , nextQuestionKey
                        );
                        if (c.moveToFirst()) {
                            newStoryProgressPart.setTitle(c.getString(c.getColumnIndex(Tables.Question.TITLE)));
                        }
                        c.close();

                        newStoryProgressPart.setStoryProgressKey(storyProgressKey);
                        newStoryProgressPart.setQuestionKey(nextQuestionKey);

                        storyProgressPartMapper.insert(newStoryProgressPart);

                    }
                }
            });

        }
    }

    private int isCorrect(Question question, String answer) {
        int state;

        if (question.getAnswer().equals(answer)) {
            state = StoryProgressPart.STATE_PASS;
        } else {
            state = StoryProgressPart.STATE_FAIL;
        }

        return state;
    }

    @Subscribe public void onNextButtonClicked(OnNextQuestionClickedEvent event) {
        Intent intent = new Intent(this, StorytellingService.class);

        intent.setAction(StorytellingService.ACTION_NEXT);

        startService(intent);
    }

    @Subscribe public void onAnswerFieldClicked(OnAnswerFieldClickedEvent event) {
        CalculatorFragment fragment = (CalculatorFragment) getSupportFragmentManager()
                .findFragmentByTag(CALCULATOR_FRAGMENT);
        fragment.releaseFocus();
    }

    @Subscribe public void onCalculatorResultAreaClicked(OnCalculatorResultAreaClickedEvent event) {
        questionCardView.releaseFocus();

        CalculatorFragment fragment = (CalculatorFragment) getSupportFragmentManager()
                .findFragmentByTag(CALCULATOR_FRAGMENT);
        fragment.gainFocus();
    }

    @Subscribe public void onCalculatorNumpadClicked(OnNumpadOperationClickedEvent event) {
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
                List<Card> cards = new ArrayList<>();

                QuestionExplanationView explanationView = new QuestionExplanationView(QuestionActivity.this);
                explanationView.setQuestion(question);

                cards.add(explanationView);
                cardViewPager.addCards(cards);
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
                cardViewPager.setCurrentItem(2);
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

package com.shinav.mathapp.question;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.mapper.ApproachMapper;
import com.shinav.mathapp.db.mapper.ApproachPartMapper;
import com.shinav.mathapp.db.mapper.QuestionMapper;
import com.shinav.mathapp.db.mapper.StoryProgressPartMapper;
import com.shinav.mathapp.db.pojo.Approach;
import com.shinav.mathapp.db.pojo.ApproachPart;
import com.shinav.mathapp.db.pojo.Question;
import com.shinav.mathapp.db.pojo.StoryProgressPart;
import com.shinav.mathapp.event.OnAnswerSubmittedEvent;
import com.shinav.mathapp.event.OnCalculatorResultAreaClickedEvent;
import com.shinav.mathapp.event.OnNextQuestionClickedEvent;
import com.shinav.mathapp.event.OnNumpadOperationClickedEvent;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.progress.Storyteller;
import com.shinav.mathapp.question.card.QuestionAnswerCardView;
import com.shinav.mathapp.question.card.QuestionApproachCardView;
import com.shinav.mathapp.question.card.QuestionCardView;
import com.shinav.mathapp.question.card.QuestionExplanationView;
import com.shinav.mathapp.question.card.QuestionNextCardView;
import com.shinav.mathapp.question.event.OnAnswerFieldClickedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func2;

public class QuestionActivity extends ActionBarActivity {

    public static final String CALCULATOR_FRAGMENT = "CalculatorFragment";

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.activity_container) RelativeLayout activityContainer;
    @InjectView(R.id.card_view_pager) CardViewPager cardViewPager;
    @InjectView(R.id.view_pager_indicator_container) LinearLayout viewPagerIndicator;
    @InjectView(R.id.calculator_container) RelativeLayout calculatorContainer;

    @Inject Bus bus;
    @Inject Storyteller storyTeller;

    @Inject SqlBrite db;
    @Inject QuestionMapper questionMapper;
    @Inject ApproachMapper approachMapper;
    @Inject ApproachPartMapper approachPartMapper;
    @Inject StoryProgressPartMapper storyProgressPartMapper;

    @Inject QuestionApproachCardView questionApproachCardView;
    @Inject QuestionCardView questionCardView;
    @Inject QuestionNextCardView questionNextCardView;

    private Question question;

    private Subscription questionSubscription;
    private Subscription approachSubscription;
    private Subscription approachPartSubscription;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ButterKnife.inject(this);
        ComponentFactory.getActivityComponent(this).inject(this);

        initCalculator();
    }

    @Override protected void onResume() {
        super.onResume();

        final String questionKey = getIntent().getStringExtra(Storyteller.TYPE_KEY);


        // This is kind of what is returned by SqlBrite.createQuery
        Observable questionQuery = Observable.just(new SqlBrite.Query() {
            @Override public Cursor run() {
                return null;
            }
        });

        Observable approachQuery = Observable.just(new SqlBrite.Query() {
            @Override public Cursor run() {
                return null;
            }
        });

        Observable.combineLatest(questionQuery, approachQuery, new Func2<SqlBrite.Query, SqlBrite.Query, Question>() {
            @Override public Question call(SqlBrite.Query qQuery, SqlBrite.Query aQuery) {

                Cursor qCursor = qQuery.run();
                // create question from cursor
                Cursor aCursor = aQuery.run();
                // set question.approaches from aQuery

                // return complete question
                return null;
            }
        });


        questionSubscription = questionMapper.getByKey(
                questionKey, new Action1<Question>() {

                    @Override public void call(Question question) {
                        QuestionActivity.this.question = question;
                        initToolbar(question.getTitle());

                        approachSubscription = approachMapper.getApproachByQuestionKey(
                                questionKey, new Action1<Approach>() {

                                    @Override public void call(Approach approach) {

                                        approachPartSubscription = approachPartMapper.getApproachPartsByApproachKey(
                                                approach.getKey(), new Action1<List<ApproachPart>>() {

                                                    @Override
                                                    public void call(List<ApproachPart> approachParts) {
                                                        initViewPager(approachParts);
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

    @Override public void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override public void onStop() {
        super.onStop();
        bus.unregister(this);
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
        updateStoryProgress(event);
    }

    private void updateStoryProgress(final OnAnswerSubmittedEvent event) {

        Cursor c = db.query(
                "SELECT * FROM " + Tables.StoryProgressPart.TABLE_NAME +
                        " WHERE " + Tables.StoryProgressPart.QUESTION_KEY + " = ?"
                , question.getKey()
        );
        if (c.moveToFirst()) {

            StoryProgressPart storyProgressPart = storyProgressPartMapper.fromCursor(c);

            storyProgressPart.setState(isCorrect(question, event.getAnswer()));
            storyProgressPartMapper.update(storyProgressPart);

            // Create progress part for next question if not there.
            final String storyProgressKey = storyProgressPart.getStoryProgressKey();
            final String nextQuestionKey = storyTeller.getNextQuestionKey();
            if (!TextUtils.isEmpty(nextQuestionKey)) {
                Cursor c2 = db.query(
                        "SELECT * FROM " + Tables.StoryProgressPart.TABLE_NAME +
                                " WHERE " + Tables.StoryProgressPart.QUESTION_KEY + " = ?"
                        , nextQuestionKey
                );

                if (!c2.moveToFirst()) {
                    StoryProgressPart newStoryProgressPart = new StoryProgressPart();

                    Cursor c3 = db.query(
                            "SELECT * FROM " + Tables.Question.TABLE_NAME +
                                    " WHERE " + Tables.Question.KEY + " = ?"
                            , nextQuestionKey
                    );
                    if (c3.moveToFirst()) {
                       newStoryProgressPart.setTitle(c3.getString(c3.getColumnIndex(Tables.Question.TITLE)));
                    }
                    c3.close();

                    newStoryProgressPart.setStoryProgressKey(storyProgressKey);
                    newStoryProgressPart.setQuestionKey(nextQuestionKey);

                    storyProgressPartMapper.insert(newStoryProgressPart);
                }
                c2.close();

            }
        }
        c.close();
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
        storyTeller.next();
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
                cards.add(new QuestionExplanationView(QuestionActivity.this));
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

package com.shinav.mathapp.question;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.shinav.mathapp.db.mapper.ApproachPartListMapper;
import com.shinav.mathapp.db.mapper.QuestionMapper;
import com.shinav.mathapp.db.mapper.StoryProgressPartMapper;
import com.shinav.mathapp.db.model.Approach;
import com.shinav.mathapp.db.model.ApproachPart;
import com.shinav.mathapp.db.model.Question;
import com.shinav.mathapp.db.model.StoryProgressPart;
import com.shinav.mathapp.event.OnAnswerSubmittedEvent;
import com.shinav.mathapp.event.OnCalculatorResultAreaClickedEvent;
import com.shinav.mathapp.event.OnNextQuestionClickedEvent;
import com.shinav.mathapp.event.OnNumpadOperationClickedEvent;
import com.shinav.mathapp.injection.InjectedActionBarActivity;
import com.shinav.mathapp.injection.module.ActivityModule;
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
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class QuestionActivity extends InjectedActionBarActivity {

    public static final String CALCULATOR_FRAGMENT = "CalculatorFragment";

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.activity_container) RelativeLayout activityContainer;
    @InjectView(R.id.card_view_pager) CardViewPager cardViewPager;
    @InjectView(R.id.view_pager_indicator_container) LinearLayout viewPagerIndicator;
    @InjectView(R.id.calculator_container) RelativeLayout calculatorContainer;

    @Inject Bus bus;
    @Inject Storyteller storyTeller;
    @Inject SqlBrite db;

    @Inject QuestionApproachCardView questionApproachCardView;
    @Inject QuestionCardView questionCardView;
    @Inject QuestionNextCardView questionNextCardView;

    private Question question;

    private Subscription questionSubscription;
    private Subscription approachSubscription;
    private Subscription approachPartSubscription;
    private Subscription updateStoryProgressSubscription;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.inject(this);

        initCalculator();
    }

    @Override public ActivityModule getModules() {
        return new ActivityModule(this);
    }


    @Override protected void onResume() {
        super.onResume();

        final String questionKey = getIntent().getStringExtra(Storyteller.TYPE_KEY);

        questionSubscription = db.createQuery(
                Tables.Question.TABLE_NAME,
                "SELECT * FROM " + Tables.Question.TABLE_NAME +
                        " WHERE " + Tables.Question.KEY + " = ?"
                , questionKey
        )
                .map(new QuestionMapper())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Question>() {

                    @Override public void call(Question question) {
                        QuestionActivity.this.question = question;
                        initToolbar();
                        fetchApproach(questionKey);
                    }
                });
    }

    private void fetchApproach(String questionKey) {
        approachSubscription = db.createQuery(
                Tables.Approach.TABLE_NAME,
                "SELECT * FROM " + Tables.Approach.TABLE_NAME +
                        " WHERE " + Tables.Approach.QUESTION_KEY + " = ?"
                , questionKey
        )
                .map(new ApproachMapper())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Approach>() {
                    @Override public void call(Approach approach) {

                        approachPartSubscription = db.createQuery(
                                Tables.ApproachPart.TABLE_NAME,
                                "SELECT * FROM " + Tables.ApproachPart.TABLE_NAME +
                                        " WHERE " + Tables.ApproachPart.APPROACH_KEY + " = ?"
                                , approach.getKey()
                        )
                                .map(new ApproachPartListMapper())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<List<ApproachPart>>() {
                                    @Override public void call(List<ApproachPart> approachParts) {
                                        initViewPager(approachParts);
                                    }
                                });

                    }
                });
    }

//    private void testing() {
//
//        String questionKey = getIntent().getStringExtra(Storyteller.TYPE_KEY);
//
//        String questionTable = Tables.Question.TABLE_NAME;
//        String approachTable = Tables.Approach.TABLE_NAME;
//        String approachPartTable = Tables.ApproachPart.TABLE_NAME;
//
//        Cursor c = db.query(
//                "SELECT * FROM " + questionTable +
//
//                        " LEFT JOIN " + approachTable + " ON " +
//                        questionTable + "." + Tables.Question.KEY + " = " +
//                        approachTable + "." + Tables.Approach.QUESTION_KEY +
//
//                        " LEFT JOIN " + approachPartTable + " ON " +
//                        approachTable + "." + Tables.Approach.KEY + " = " +
//                        approachPartTable + "." + Tables.ApproachPart.APPROACH_KEY +
//
//                        " WHERE " + questionTable + "." + Tables.Question.KEY + " = ?"
//                , questionKey
//        );
//
//    }

    @Override protected void onPause() {
        super.onPause();
        questionSubscription.unsubscribe();
        approachSubscription.unsubscribe();
        approachPartSubscription.unsubscribe();
        updateStoryProgressSubscription.unsubscribe();
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

    private void initToolbar() {
        toolbar.setTitle(question.getTitle());
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

        questionCardView.setQuestion(question);
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

        updateStoryProgressSubscription = db.createQuery(
                Tables.StoryProgressPart.TABLE_NAME,
                "SELECT * FROM " + Tables.StoryProgressPart.TABLE_NAME +
                        " WHERE " + Tables.StoryProgressPart.QUESTION_KEY + " = ?"
                , event.getQuestion().getKey()
        )
                .map(new StoryProgressPartMapper())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<StoryProgressPart>() {
                    @Override public void call(StoryProgressPart storyProgressPart) {

                        ContentValues values = storyProgressPart.getContentValues();
                        values.put(Tables.StoryProgressPart.GIVEN_ANSWER, event.getAnswer());

                        db.update(
                                Tables.StoryProgressPart.TABLE_NAME,
                                values,
                                "key=?",
                                values.getAsString(Tables.StoryProgressPart.KEY)
                        );

                        // Create progress part for next question.
                        String storyProgressKey = storyProgressPart.getStoryProgressKey();
                        String nextQuestionKey = storyTeller.getNextQuestionKey();

                        StoryProgressPart newStoryProgressPart = new StoryProgressPart();

                        newStoryProgressPart.setStoryProgressKey(storyProgressKey);
                        newStoryProgressPart.setGivenAnswer(null);
                        newStoryProgressPart.setQuestionKey(nextQuestionKey);

                        db.insert(Tables.StoryProgressPart.TABLE_NAME, newStoryProgressPart.getContentValues());

                    }
                });
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

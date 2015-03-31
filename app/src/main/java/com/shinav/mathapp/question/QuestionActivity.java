package com.shinav.mathapp.question;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import com.shinav.mathapp.repository.RealmRepository;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class QuestionActivity extends InjectedActionBarActivity {

    public static final String CALCULATOR_FRAGMENT = "CalculatorFragment";

    @InjectView(R.id.activity_container) RelativeLayout activityContainer;
    @InjectView(R.id.card_view_pager) CardViewPager cardViewPager;
    @InjectView(R.id.view_pager_indicator_container) LinearLayout viewPagerIndicator;
    @InjectView(R.id.calculator_container) RelativeLayout calculatorContainer;

    private QuestionCardView questionCardView;
    private Question question;
    @InjectView(R.id.toolbar) Toolbar toolbar;

    @Inject Bus bus;
    @Inject RealmRepository realmRepository;
    @Inject Storyteller storyTeller;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.inject(this);

//        String questionKey = getIntent().getStringExtra(Storyteller.TYPE_KEY);
        String questionKey = "question-1";
        question = realmRepository.getQuestion(questionKey);

        initToolbar();
        initViewPager();
        initCalculator();
    }

    @Override public ActivityModule getModules() {
        return new ActivityModule(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    public void onStop() {
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

    @Override
    public void onBackPressed() {
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

    private void initViewPager() {
        List<Card> cards = new ArrayList<>();

        QuestionApproachCardView questionApproachCardView = new QuestionApproachCardView(this);
        questionApproachCardView.setApproach(question.getApproach());
        cards.add(questionApproachCardView);

        questionCardView = new QuestionCardView(this, bus);
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

    @Subscribe
    public void OnAnswerSubmittedEvent(OnAnswerSubmittedEvent event) {
        startAnimation(event.getAnswer());
        questionCardView.setAnswerFieldEnabled(false);
        questionCardView.setSubmitButtonEnabled(false);
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

        final QuestionNextCardView questionNextCardView = new QuestionNextCardView(this, bus);
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

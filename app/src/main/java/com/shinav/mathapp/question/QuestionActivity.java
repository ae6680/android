package com.shinav.mathapp.question;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.R;
import com.shinav.mathapp.animation.YAnimation;
import com.shinav.mathapp.bus.BusProvider;
import com.shinav.mathapp.calculator.CalculatorFragment;
import com.shinav.mathapp.event.OnAnswerSubmittedEvent;
import com.shinav.mathapp.event.OnNextQuestionClickedEvent;
import com.shinav.mathapp.progress.Storyteller;
import com.shinav.mathapp.repository.RealmRepository;
import com.shinav.mathapp.view.Card;
import com.shinav.mathapp.view.CardViewPager;
import com.squareup.otto.Subscribe;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class QuestionActivity extends ActionBarActivity {

    public static final String CALCULATOR_FRAGMENT = "CalculatorFragment";

    @InjectView(R.id.activity_container) RelativeLayout activityContainer;
    @InjectView(R.id.card_view_pager) CardViewPager cardViewPager;
    @InjectView(R.id.view_pager_indicator_container) LinearLayout viewPagerIndicator;
    @InjectView(R.id.calculator_container) RelativeLayout calculatorContainer;

    private QuestionCardView questionCardView;
    private Question question;
    @InjectView(R.id.toolbar) Toolbar toolbar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.inject(this);

//        String questionKey = getIntent().getStringExtra(Storyteller.TYPE_KEY);
        String questionKey = "question-1";
        question = RealmRepository.getInstance().getQuestion(questionKey);

        initToolbar();

        initViewPager();

        initCalculator();
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

    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getUIBusInstance().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.getUIBusInstance().unregister(this);
    }

    private void initViewPager() {
        questionCardView = new QuestionCardView(this);
        questionCardView.setQuestion(question);

        cardViewPager.setIndicator(viewPagerIndicator);
        cardViewPager.setCards(Arrays.<Card>asList(
                new QuestionCardView(this),
                questionCardView,
                new QuestionCardView(this)
        ));
        cardViewPager.setCurrentItem(1);
    }

    private void initCalculator() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.calculator_container, new CalculatorFragment(), CALCULATOR_FRAGMENT).commit();
    }

    @Subscribe
    public void OnAnswerSubmittedEvent(OnAnswerSubmittedEvent event) {
        startAnimation(event.getAnswer());
    }

    private void startAnimation(String answer) {
        AnimatorSet set = new AnimatorSet();

        float originalScaleX = viewPagerIndicator.getScaleX();
        float originalScaleY = viewPagerIndicator.getScaleY();
        float questionResultY = MyApplication.screenHeight * 0.62f;
        float questionNextY = MyApplication.screenHeight * 0.84f;

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(viewPagerIndicator,
                "scaleX", originalScaleX, 0f);

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(viewPagerIndicator,
                "scaleY", originalScaleY, 0f);

        ObjectAnimator anim3 = YAnimation.create(calculatorContainer, 1000);

        final QuestionResultCardView questionResultCard = new QuestionResultCardView(this);
        questionResultCard.showCorrect();
        activityContainer.addView(questionResultCard);

        ObjectAnimator anim4 = ObjectAnimator.ofFloat(questionResultCard,
                "Y", 3000, questionResultY);

        anim4.addListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) {
                questionResultCard.setVisibility(View.VISIBLE);
            }

            @Override public void onAnimationEnd(Animator animation) {  }

            @Override public void onAnimationCancel(Animator animation) {  }

            @Override public void onAnimationRepeat(Animator animation) {  }
        });

        final QuestionNextCardView questionNextCardView = new QuestionNextCardView(this);
        questionNextCardView.setGivenAnswer(answer);
        activityContainer.addView(questionNextCardView);

        ObjectAnimator anim5 = ObjectAnimator.ofFloat(questionNextCardView,
                "Y", 3000, questionNextY);

        anim5.addListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) {
                questionNextCardView.setVisibility(View.VISIBLE);
            }

            @Override public void onAnimationEnd(Animator animation) {  }

            @Override public void onAnimationCancel(Animator animation) {  }

            @Override public void onAnimationRepeat(Animator animation) {  }
        });

        ObjectAnimator anim6 = ObjectAnimator.ofFloat(viewPagerIndicator,
                "scaleX", 0f, originalScaleX);

        ObjectAnimator anim7 = ObjectAnimator.ofFloat(viewPagerIndicator,
                "scaleY", 0f, originalScaleY);

        set.play(anim1).with(anim2);
        set.play(anim3).after(100);
        set.play(anim4).after(600);
        set.play(anim5).after(1000);
        set.play(anim6).with(anim7).after(1500);

        set.start();
    }

    @Subscribe
    public void onNextButtonClicked(OnNextQuestionClickedEvent event) {
        new Storyteller(this).next();
    }

    public void onAnswerChanged(String answer) {
        questionCardView.onAnswerChanged(answer);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_from_outside, R.anim.slide_right_to_outside);
    }

}

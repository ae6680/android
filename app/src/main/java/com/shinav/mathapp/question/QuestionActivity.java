package com.shinav.mathapp.question;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.shinav.mathapp.R;
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

public class QuestionActivity extends FragmentActivity {

    public static final String CALCULATOR_FRAGMENT = "CalculatorFragment";

    @InjectView(R.id.card_view_pager) CardViewPager cardViewPager;

    private QuestionCardView questionCardView;
    private Question question;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ButterKnife.inject(this);

//        String questionKey = getIntent().getStringExtra(Storyteller.TYPE_KEY);
        String questionKey = "question-1";
        question = RealmRepository.getInstance().getQuestion(questionKey);

        questionCardView = new QuestionCardView(this);
        questionCardView.setQuestion(question);

        cardViewPager.setCards(Arrays.<Card>asList(
                questionCardView
        ));

        initCalculator();
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

    private void initCalculator() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.calculator_container, new CalculatorFragment(), CALCULATOR_FRAGMENT).commit();
    }

    @Subscribe
    public void OnAnswerSubmittedEvent(OnAnswerSubmittedEvent event) {
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

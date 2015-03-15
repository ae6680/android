package com.shinav.mathapp.question;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.shinav.mathapp.R;
import com.shinav.mathapp.animation.AnimationFactory;
import com.shinav.mathapp.approach.ApproachActivity;
import com.shinav.mathapp.bus.BusProvider;
import com.shinav.mathapp.calculator.CalculatorFragment;
import com.shinav.mathapp.event.OnAnswerSubmittedEvent;
import com.shinav.mathapp.event.OnNextQuestionClickedEvent;
import com.shinav.mathapp.progress.ProgressProvider;
import com.shinav.mathapp.view.CustomViewFlipper;
import com.shinav.mathapp.view.FlipCard;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;

public class QuestionActivity extends FragmentActivity {

    public static final String CALCULATOR_FRAGMENT = "CalculatorFragment";

    @InjectView(R.id.view_flipper) CustomViewFlipper viewFlipper;
    private QuestionView questionView;
    private Question question;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_question);

        ButterKnife.inject(this);

        question = ProgressProvider.getCurrentQuestion();

        questionView = new QuestionView(this, question);
        viewFlipper.setFront(questionView);

        viewFlipper.setBack(new QuestionApproachView(this));

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
        if (event.getQuestionKey().equals(question.getFirebaseKey())) {

            FlipCard backView;

            if (event.getAnswer().equals(question.getAnswer())) {
                backView = new QuestionPassView(this);
            } else {
                QuestionFailView failView = new QuestionFailView(this);
                failView.setAnswer(question.getAnswer());
                backView = failView;
            }

            viewFlipper.setBack(backView);
            AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT, 300);
            viewFlipper.setFront(new QuestionExplanationView(this));
        }
    }

    @Subscribe
    public void onNextButtonClicked(OnNextQuestionClickedEvent event) {

        List<Question> questions = new ArrayList<>(Realm.getInstance(this).where(Question.class).findAll());
        int pos = questions.indexOf(question)+1 < questions.size() ? questions.indexOf(question)+1 : questions.indexOf(question);
        ProgressProvider.setCurrentQuestion(questions.get(pos));

        startActivity(new Intent(this, ApproachActivity.class));
        overridePendingTransition(R.anim.slide_left_from_outside, R.anim.slide_left_to_outside);
    }

    public void onAnswerChanged(String answer) {
        questionView.onAnswerChanged(answer);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_from_outside, R.anim.slide_right_to_outside);
    }

}

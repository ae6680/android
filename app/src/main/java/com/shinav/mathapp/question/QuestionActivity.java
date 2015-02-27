package com.shinav.mathapp.question;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.calculator.CalculatorFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class QuestionActivity extends FragmentActivity {

    public static final String CALCULATOR_FRAGMENT = "CalculatorFragment";
    public static final String QUESTION_HINT_FRAGMENT = "QuestionHintFragment";
    public static final String QUESTION_ANSWER_DIALOG_FRAGMENT = "QuestionAnswerDialogFragment";
    @InjectView(R.id.question_fase) TextView questionFase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ButterKnife.inject(this);

        questionFase.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        initCalculator();
    }

    private void initCalculator() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.calculator_container, new CalculatorFragment(), CALCULATOR_FRAGMENT).commit();
    }

    @OnClick(R.id.hint)
    public void onHintClicked() {
        new QuestionHintDialogFragment().show(getFragmentManager(), QUESTION_HINT_FRAGMENT);
    }

    @OnClick(R.id.answer_button)
    public void onAnswerClicked() {
        CalculatorFragment calculatorFragment = (CalculatorFragment) getSupportFragmentManager().findFragmentByTag(CALCULATOR_FRAGMENT);
        String lastCalculatorAnswer = calculatorFragment.onAnswerClicked();

        QuestionAnswerDialogFragment.newInstance(lastCalculatorAnswer)
                .show(getFragmentManager(), QUESTION_ANSWER_DIALOG_FRAGMENT);
    }

}

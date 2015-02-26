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
                .add(R.id.calculator_container, new CalculatorFragment()).commit();
    }

    @OnClick(R.id.hint)
    public void onHintClicked() {
        new QuestionHintDialogFragment().show(getFragmentManager(), "QuestionHintFragment");
    }

    @OnClick(R.id.answer_button)
    public void onAnswerClicked() {
        new QuestionAnswerDialogFragment().show(getFragmentManager(), "QuestionAnswerDialogFragment");
    }

}

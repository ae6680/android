package com.shinav.mathapp.question;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.calculator.CalculatorFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class QuestionFragment extends Fragment {

    public static final String CALCULATOR_FRAGMENT = "CalculatorFragment";
    public static final String QUESTION_HINT_FRAGMENT = "QuestionHintFragment";
    public static final String QUESTION_ANSWER_DIALOG_FRAGMENT = "QuestionAnswerDialogFragment";

    private static final String QUESTION = "question";

    @InjectView(R.id.question_title) TextView questionTitle;
    @InjectView(R.id.question) TextView questionBody;

    private Question question;

    public static QuestionFragment newInstance(Question question) {
        QuestionFragment fragment = new QuestionFragment();

        Bundle args = new Bundle();
        args.putSerializable(QUESTION, question);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        question = (Question) getArguments().getSerializable(QUESTION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        ButterKnife.inject(this, view);

        questionTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        questionTitle.setText(question.getTitle());

        questionBody.setText(question.getValue());

        initCalculator();

        return view;
    }

    private void initCalculator() {
        getChildFragmentManager().beginTransaction()
                .add(R.id.calculator_container, new CalculatorFragment(), CALCULATOR_FRAGMENT).commit();
    }

    @OnClick(R.id.hint)
    public void onHintClicked() {
        new QuestionHintDialogFragment()
                .show(getActivity().getSupportFragmentManager(), QUESTION_HINT_FRAGMENT);
    }

    @OnClick(R.id.answer_button)
    public void onAnswerClicked() {
        CalculatorFragment calculatorFragment = (CalculatorFragment) getChildFragmentManager()
                .findFragmentByTag(CALCULATOR_FRAGMENT);

        String lastCalculatorAnswer = calculatorFragment.onAnswerClicked();

        QuestionAnswerDialogFragment.newInstance(lastCalculatorAnswer)
                .show(getActivity().getSupportFragmentManager(), QUESTION_ANSWER_DIALOG_FRAGMENT);
    }

}

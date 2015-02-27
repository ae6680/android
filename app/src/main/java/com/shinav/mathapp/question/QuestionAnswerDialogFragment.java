package com.shinav.mathapp.question;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.shinav.mathapp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class QuestionAnswerDialogFragment extends DialogFragment {

    private static final String LAST_CALCULATOR_ANSWER = "lastCalculatorAnswer";

    public static final String QUESTION_PASS_DIALOG_FRAGMENT = "QuestionPassDialogFragment";
    public static final String QUESTION_FAIL_DIALOG_FRAGMENT = "QuestionFailDialogFragment";

    @InjectView(R.id.dialog_answer_title) TextView title;
    @InjectView(R.id.dialog_answer) TextView answer;

    public static QuestionAnswerDialogFragment newInstance(String lastCalculatorAnswer) {

        QuestionAnswerDialogFragment fragment = new QuestionAnswerDialogFragment();
        Bundle args = new Bundle();
        args.putString(LAST_CALCULATOR_ANSWER, lastCalculatorAnswer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        View view = inflater.inflate(R.layout.dialog_question_answer, container, false);

        ButterKnife.inject(this, view);

        setLastCalculatorAnswer();

        title.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        return view;
    }

    private void setLastCalculatorAnswer() {
        String lastCalculatorAnswer = getArguments().getString(LAST_CALCULATOR_ANSWER, "");
        if (!TextUtils.isEmpty(lastCalculatorAnswer)) {
           answer.setText(lastCalculatorAnswer);
        }
    }

    @OnClick(R.id.answer_button)
    public void onAnswerSubmit() {

        if (answer.getText().equals("150,5")) {
            new QuestionPassDialogFragment().show(getActivity().getSupportFragmentManager(), QUESTION_PASS_DIALOG_FRAGMENT);
        } else {
            new QuestionFailDialogFragment().show(getActivity().getSupportFragmentManager(), QUESTION_FAIL_DIALOG_FRAGMENT);
        }

        dismiss();
    }

}

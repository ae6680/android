package com.shinav.mathapp.question;

import android.app.DialogFragment;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shinav.mathapp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class QuestionAnswerDialogFragment extends DialogFragment {

    @InjectView(R.id.dialog_answer_title) TextView title;
    @InjectView(R.id.dialog_answer) TextView answer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        View view = inflater.inflate(R.layout.dialog_question_answer, container, false);
        ButterKnife.inject(this, view);

        title.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        return view;
    }

    @OnClick(R.id.answer_button)
    public void onAnswerSubmit() {

        if (answer.getText().equals("150,5")) {
            new QuestionPassDialogFragment().show(getFragmentManager(), "QuestionPassDialogFragment");
        } else {
            new QuestionFailDialogFragment().show(getFragmentManager(), "QuestionFailDialogFragment");
        }
    }

}

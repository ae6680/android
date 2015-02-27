package com.shinav.mathapp.question;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.bus.BusProvider;
import com.shinav.mathapp.event.OnNextQuestionClickedEvent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class QuestionPassDialogFragment extends DialogFragment {

    @InjectView(R.id.dialog_pass_title) TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        View view = inflater.inflate(R.layout.dialog_question_pass, container, false);
        ButterKnife.inject(this, view);

        title.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        return view;
    }

    @OnClick(R.id.next_question_button)
    public void onNextQuestion() {
        BusProvider.getUIBusInstance().post(new OnNextQuestionClickedEvent());
        dismiss();
    }

}

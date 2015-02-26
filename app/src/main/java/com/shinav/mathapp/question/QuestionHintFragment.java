package com.shinav.mathapp.question;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.shinav.mathapp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class QuestionHintFragment extends DialogFragment {

    @InjectView(R.id.hint_title) TextView hintTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        View view = inflater.inflate(R.layout.dialog_question_hint, container, false);
        ButterKnife.inject(this, view);

        hintTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        return view;
    }

    @OnClick(R.id.close)
    public void onClose() {
        this.dismiss();
    }

}

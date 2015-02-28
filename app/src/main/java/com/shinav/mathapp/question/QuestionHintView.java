package com.shinav.mathapp.question;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.animation.AnimationFactory;
import com.shinav.mathapp.view.FlipCard;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class QuestionHintView extends FlipCard {

    @InjectView(R.id.hint_title) TextView hintTitle;
    @InjectView(R.id.hint_body) TextView hintBody;

    public QuestionHintView(Context context) {
        super(context);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.question_hint_card, null, false);

        ButterKnife.inject(this, view);

        setParams(view);
        setTitleUnderline();

        addView(view);
    }

    private void setTitleUnderline() {
        hintTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @OnClick(R.id.hint_close)
    public void onHintCloseClicked() {
        flip(AnimationFactory.FlipDirection.LEFT_RIGHT, 300);
    }

    public void setTitle(String title) {
        hintTitle.setText(title);
    }

    public void setBody(String value) {
        hintBody.setText(value);
    }

}

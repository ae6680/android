package com.shinav.mathapp.question;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.shinav.mathapp.R;
import com.shinav.mathapp.animation.AnimationFactory;
import com.shinav.mathapp.bus.BusProvider;
import com.shinav.mathapp.event.OnAnswerSubmittedEvent;
import com.shinav.mathapp.view.FlipCard;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class QuestionView extends FlipCard {

    @InjectView(R.id.question_title) TextView questionTitle;
    @InjectView(R.id.question) TextView questionBody;

    @InjectView(R.id.answer_flipper) ViewFlipper answerFlipper;
    @InjectView(R.id.answer_field) TextView answerField;

    @InjectView(R.id.submit_button) ImageButton submitButton;

    private final Context context;
    private final Question question;

    public QuestionView(Context context, Question question) {
        super(context);
        this.context = context;
        this.question = question;
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.question_card, null, false);

        ButterKnife.inject(this, view);

        setParams(view);
        submitButton.setEnabled(false);

        setBody(question.getValue());
        setTitle(question.getTitle());

        addView(view);
    }

    public void setBody(String value) {
        questionBody.setText(value);
    }

    public void setTitle(String title) {
        questionTitle.setText(title);
    }

    @OnClick(R.id.hint)
    public void onHintClicked() {
        flip(AnimationFactory.FlipDirection.LEFT_RIGHT, 300);
    }

    @OnClick(R.id.answer_button)
    public void onAnswerClicked() {
        AnimationFactory.flipTransition(answerFlipper, AnimationFactory.FlipDirection.BOTTOM_TOP, 300);
        submitButton.setEnabled(true);
    }

    @OnClick(R.id.submit_button)
    public void onSubmitClicked() {
        String answer = answerField.getText().toString();
        OnAnswerSubmittedEvent event = new OnAnswerSubmittedEvent(question.getFirebaseKey(), answer);
        BusProvider.getUIBusInstance().post(event);
    }

    public void onAnswerChanged(String answer) {
        answerField.setText(answer);
    }

}

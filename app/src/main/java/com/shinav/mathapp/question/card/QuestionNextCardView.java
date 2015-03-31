package com.shinav.mathapp.question.card;

import android.annotation.SuppressLint;
import android.content.Context;

import com.shinav.mathapp.R;
import com.shinav.mathapp.event.OnNextQuestionClickedEvent;
import com.shinav.mathapp.view.ButterKnifeLayout;
import com.squareup.otto.Bus;

import butterknife.OnClick;

@SuppressLint("ViewConstructor")
public class QuestionNextCardView extends ButterKnifeLayout {

    private final Bus bus;

    public QuestionNextCardView(Context context, Bus bus) {
        super(context);
        this.bus = bus;
        init();
    }

    private void init() {
        inflate(R.layout.question_next_card, this, true);
    }

    @OnClick(R.id.next_question_button)
    public void onNextButtonClicked() {
        bus.post(new OnNextQuestionClickedEvent());
    }

}

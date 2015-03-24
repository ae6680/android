package com.shinav.mathapp.question.cards;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.shinav.mathapp.R;
import com.shinav.mathapp.event.OnNextQuestionClickedEvent;
import com.squareup.otto.Bus;

import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("ViewConstructor")
public class QuestionNextCardView extends RelativeLayout {

    private final Bus bus;

    public QuestionNextCardView(Context context, Bus bus) {
        super(context);
        this.bus = bus;
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View view = inflater.inflate(R.layout.question_next_card, this, true);
        ButterKnife.inject(this, view);
    }

    @OnClick(R.id.next_question_button)
    public void onNextButtonClicked() {
        bus.post(new OnNextQuestionClickedEvent());
    }

}

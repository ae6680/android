package com.shinav.mathapp.question.card;

import android.content.Context;

import com.shinav.mathapp.R;
import com.shinav.mathapp.event.NextQuestionClickedEvent;
import com.shinav.mathapp.injection.annotation.ForActivity;
import com.shinav.mathapp.view.ButterKnifeLayout;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.OnClick;

public class QuestionNextCardView extends ButterKnifeLayout {

    private final Bus bus;

    @Inject
    public QuestionNextCardView(@ForActivity Context context, Bus bus) {
        super(context);
        this.bus = bus;
        init();
    }

    private void init() {
        inflate(R.layout.question_next_card, this, true);
    }

    @OnClick(R.id.next_question_button)
    public void onNextButtonClicked() {
        bus.post(new NextQuestionClickedEvent());
    }

}

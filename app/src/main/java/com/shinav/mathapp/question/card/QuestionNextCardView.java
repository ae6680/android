package com.shinav.mathapp.question.card;

import android.content.Context;

import com.shinav.mathapp.R;
import com.shinav.mathapp.event.OnNextQuestionClickedEvent;
import com.shinav.mathapp.injection.module.ViewModule;
import com.shinav.mathapp.view.ButterKnifeLayout;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.OnClick;
import dagger.ObjectGraph;

public class QuestionNextCardView extends ButterKnifeLayout {

    @Inject Bus bus;

    public QuestionNextCardView(Context context) {
        super(context);
        init();
    }

    private void init() {
        ObjectGraph.create(new ViewModule()).inject(this);
        inflate(R.layout.question_next_card, this, true);
    }

    @OnClick(R.id.next_question_button)
    public void onNextButtonClicked() {
        bus.post(new OnNextQuestionClickedEvent());
    }

}

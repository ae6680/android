package com.shinav.mathapp.question;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.shinav.mathapp.R;
import com.shinav.mathapp.bus.BusProvider;
import com.shinav.mathapp.event.OnNextQuestionClickedEvent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class QuestionNextCardView extends RelativeLayout {

    @InjectView(R.id.answer_input) EditText answerInput;

    public QuestionNextCardView(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View view = inflater.inflate(R.layout.question_next_card, this, true);
        ButterKnife.inject(this, view);

        answerInput.setEnabled(false);

        view.setVisibility(GONE);
    }

    public void setGivenAnswer(String answer) {
        answerInput.setText(answer);
    }

    @OnClick(R.id.next_question_button)
    public void onNextButtonClicked() {
        BusProvider.getUIBusInstance().post(new OnNextQuestionClickedEvent());
    }
}

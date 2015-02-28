package com.shinav.mathapp.question;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.bus.BusProvider;
import com.shinav.mathapp.event.OnNextQuestionClickedEvent;
import com.shinav.mathapp.view.FlipCard;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class QuestionFailView extends FlipCard {

    @InjectView(R.id.answer_title) TextView answerTitle;
    @InjectView(R.id.answer_message) TextView answerMessage;

    public QuestionFailView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.question_answer_fail, null, false);

        ButterKnife.inject(this, view);

        setParams(view);
        setTitleUnderline();

        addView(view);
    }

    private void setTitleUnderline() {
        answerTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    public void setAnswer(String answer) {
        answerMessage.setText("Het goede antwoord is: \n" + answer);
    }

    @OnClick(R.id.next_question_button)
    public void onNextButtonClicked() {
        BusProvider.getUIBusInstance().post(new OnNextQuestionClickedEvent());
    }

}

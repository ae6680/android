package com.shinav.mathapp.question.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.question.Question;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class QuestionAnswerCardView extends RelativeLayout {

    @InjectView(R.id.question_result_card) RelativeLayout questionResultCard;
    @InjectView(R.id.answer) TextView answerView;
    private Question question;

    public QuestionAnswerCardView(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View view = inflater.inflate(R.layout.question_result_card, this, true);
        ButterKnife.inject(this, view);
    }

    public void setQuestion(Question question) {
        this.question = question;
        answerView.setText(question.getAnswer());
    }

    public void giveAnswer(String givenAnswer) {
        if (givenAnswer.equals(question.getAnswer())) {
            showCorrect();
        } else {
            showIncorrect();
        }
    }

    private void showCorrect() {
        questionResultCard.setBackgroundResource(R.drawable.card_correct);
    }

    private void showIncorrect() {
        questionResultCard.setBackgroundResource(R.drawable.card_incorrect);
    }

}

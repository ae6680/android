package com.shinav.mathapp.question.card;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.model.Question;
import com.shinav.mathapp.view.ButterKnifeLayout;

import butterknife.InjectView;

public class QuestionAnswerCardView extends ButterKnifeLayout {

    @InjectView(R.id.question_result_card) RelativeLayout questionResultCard;
    @InjectView(R.id.answer) TextView answerView;
    
    private Question question;

    public QuestionAnswerCardView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(R.layout.question_answer_card, this, true);
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

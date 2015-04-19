package com.shinav.mathapp.question.card;

import android.content.Context;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.card.Card;
import com.shinav.mathapp.db.pojo.Question;

import butterknife.InjectView;

public class QuestionExplanationView extends Card {

    @InjectView(R.id.explanation_body) TextView explanationBody;

    public QuestionExplanationView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(R.layout.question_explanantion_card, this, true);
    }

    public void setQuestion(Question question) {
        explanationBody.setText(question.getExplanation());
    }

}

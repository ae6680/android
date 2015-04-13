package com.shinav.mathapp.question.card;

import android.content.Context;
import android.view.View;
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
        View view = inflate(R.layout.question_explanantion_card, this, false);

        setLayoutParamsForViewPager(view);

        addView(view);
    }

    public void setQuestion(Question question) {
        explanationBody.setText(question.getExplanation());
    }

}

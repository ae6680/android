package com.shinav.mathapp.questionApproach;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.card.Card;
import com.shinav.mathapp.injection.annotation.ForActivity;

import javax.inject.Inject;

import butterknife.InjectView;

public class QuestionSimpleCardView extends Card {

    @InjectView(R.id.question_text) TextView questionBody;

    @Inject
    public QuestionSimpleCardView(@ForActivity Context context) {
        super(context);
        init();
    }

    private void init() {
        View view = inflate(R.layout.question_simple_card, this, false);
        addView(view);
    }

    public void setQuestionValue(String questionValue) {
        setQuestionText(questionValue);
    }

    private void setQuestionText(String value) {
        questionBody.setText(value);
        questionBody.setMovementMethod(new ScrollingMovementMethod());
    }

}

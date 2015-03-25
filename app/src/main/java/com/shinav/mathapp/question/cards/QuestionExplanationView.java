package com.shinav.mathapp.question.cards;

import android.content.Context;
import android.view.View;

import com.shinav.mathapp.R;
import com.shinav.mathapp.view.Card;

public class QuestionExplanationView extends Card {

    public QuestionExplanationView(Context context) {
        super(context);
        init();
    }

    private void init() {
        View view = inflate(R.layout.question_explanantion_card, this, false);

        setLayoutParamsForViewPager(view);

        addView(view);
    }

}

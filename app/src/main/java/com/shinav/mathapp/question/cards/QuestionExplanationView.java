package com.shinav.mathapp.question.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.shinav.mathapp.R;
import com.shinav.mathapp.view.Card;

import butterknife.ButterKnife;

public class QuestionExplanationView extends Card {

    public QuestionExplanationView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.question_explanantion_card, this, false);

        ButterKnife.inject(this, view);

        setLayoutParamsForViewPager(view);

        addView(view);
    }

}

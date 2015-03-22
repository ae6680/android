package com.shinav.mathapp.question;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.shinav.mathapp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class QuestionAnswerCardView extends RelativeLayout {

    @InjectView(R.id.question_result_card) RelativeLayout questionResultCard;

    public QuestionAnswerCardView(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View view = inflater.inflate(R.layout.question_result_card, this, true);
        ButterKnife.inject(this, view);

        view.setVisibility(GONE);
    }

    public void showCorrect() {
        questionResultCard.setBackgroundResource(R.drawable.card_correct);
    }

    public void showIncorrect() {
        questionResultCard.setBackgroundResource(R.drawable.card_incorrect);
    }

}

package com.shinav.mathapp.question.card;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.card.Card;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;

public class QuestionExplanationCardView extends Card {

    @InjectView(R.id.explanation_body) TextView explanationBody;
    @InjectView(R.id.explanation_image) ImageView explanationImage;

    public QuestionExplanationCardView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(R.layout.question_explanantion_card, this, true);
    }

    public void setExplanationText(String explanationText) {
        explanationBody.setText(explanationText);
    }

    public void setExplanationImageUrl(String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(this.getContext())
                    .load(imageUrl)
                    .into(explanationImage);
        }
    }

}

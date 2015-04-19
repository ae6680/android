package com.shinav.mathapp.question.card;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.card.Card;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;

public class QuestionAnnexCardView extends Card {

    @InjectView(R.id.annex_image_view) ImageView imageView;

    public QuestionAnnexCardView(Context context) {
        super(context);
        init();
    }

    public void init() {
        View view = inflate(R.layout.question_annex_card, this, false);
        setLayoutParamsForViewPager(view);
        addView(view);
    }

    public void setAnnexImageUrl(String imageUrl) {
        Picasso.with(this.getContext())
                .load(imageUrl)
                .fit()
                .into(imageView);
    }

}

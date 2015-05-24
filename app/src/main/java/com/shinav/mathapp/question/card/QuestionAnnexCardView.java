package com.shinav.mathapp.question.card;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shinav.mathapp.R;
import com.shinav.mathapp.card.Card;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import butterknife.OnClick;

public class QuestionAnnexCardView extends Card {

    @InjectView(R.id.annex_image_view) ImageView imageView;
    @InjectView(R.id.internet_not_found) LinearLayout internetNotFound;

    private String annexImageUrl;

    public QuestionAnnexCardView(Context context) {
        super(context);
        init();
    }

    public void init() {
        inflate(R.layout.question_annex_card, this, true);
    }

    public void setAnnexImageUrl(String imageUrl) {
        if (isOnline()) {
            Picasso.with(this.getContext())
                    .load(imageUrl)
                    .into(imageView);
        } else {
            internetNotFound.setVisibility(VISIBLE);
            annexImageUrl = imageUrl;
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)
                this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    @OnClick(R.id.retry_internet_connection)
    public void onRetryInternetConnectionClicked() {
        internetNotFound.setVisibility(GONE);
        setAnnexImageUrl(annexImageUrl);
    }

}

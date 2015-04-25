package com.shinav.mathapp.image;

import android.content.Context;
import android.widget.ImageView;

import com.shinav.mathapp.R;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class BackgroundLoader {

    @Inject
    public BackgroundLoader() { }

    public void loadBackground(ImageView background, String imageUrl) {

        Context context = background.getContext();

        Picasso.with(context)
                .load(imageUrl)
                .centerCrop()
                .fit()
                .into(background);
        background.setImageAlpha(context.getResources().getInteger(R.integer.background_image_alpha));
    }
}

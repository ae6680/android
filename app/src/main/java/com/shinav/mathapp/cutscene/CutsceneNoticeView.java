package com.shinav.mathapp.cutscene;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.injection.component.Injector;
import com.shinav.mathapp.view.ButterKnifeLayout;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CutsceneNoticeView extends ButterKnifeLayout {

    @InjectView(R.id.cutscene_notice_text) TextView text;
    @InjectView(R.id.cutscene_notice_image) ImageView image;

    public CutsceneNoticeView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Injector.getViewComponent(getContext()).inject(this);

        View view = inflate(R.layout.cutscene_notice_view, this, false);

        ButterKnife.inject(this, view);

        addView(view);
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setImage(String imageUrl) {
        Picasso.with(this.getContext())
                .load(imageUrl)
                .centerCrop()
                .fit()
                .into(image);
    }

}

package com.shinav.mathapp.main.storyboard;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.injection.component.Injector;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.STATE_CLOSED;

public abstract class StoryboardFrameViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.title) TextView title;
    @InjectView(R.id.state) ImageButton state;
    @InjectView(R.id.background_view) ImageView background;

    @Inject Bus bus;

    private String key;
    private int currentState = 0;

    public StoryboardFrameViewHolder(View itemView) {
        super(itemView);
        Injector.getViewComponent(itemView.getContext()).inject(this);
        ButterKnife.inject(this, itemView);
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void showBackgroundLightened() {
        background.setImageAlpha(255);
    }

    public void showBackgroundDarkened() {
        background.setImageAlpha(100);
    }

    public void setBackgroundImage(String imageUrl) {
        Picasso.with(background.getContext())
                .load(imageUrl)
                .into(background);
    }

    public void setTitle(String text) {
        title.setText(text);
    }

    public void setState(int state) {
        this.currentState = state;
    }

    @OnClick(R.id.storyboard_frame_list_item)
    public void onStoryboardFrameListItemClicked() {
        if (currentState != STATE_CLOSED) {
            bus.post(new StoryboardFrameListItemClicked(key));
        }
    }

}

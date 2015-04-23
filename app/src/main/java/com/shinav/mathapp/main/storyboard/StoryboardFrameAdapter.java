package com.shinav.mathapp.main.storyboard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.STATE_CLOSED;
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.STATE_FAILED;
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.STATE_OPENED;
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.STATE_PASSED;

public class StoryboardFrameAdapter extends RecyclerView.Adapter<StoryboardFrameAdapter.ViewHolder> {

    @Inject Bus bus;

    private List<StoryboardFrameListItem> listItems = Collections.emptyList();

    @Inject public StoryboardFrameAdapter() { }

    @Override public StoryboardFrameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.storyboard_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(StoryboardFrameAdapter.ViewHolder holder, int position) {
        StoryboardFrameListItem listItem = listItems.get(position);

        holder.setKey(listItem.getKey());

        setTitle(holder.title, listItem.getTitle());
        setState(holder, listItem.getState());
        setBackgroundImage(holder.background, listItem.getBackgroundImage());
    }

    @Override public int getItemCount() {
        return listItems.size();
    }

    private void setTitle(TextView title, String text) {
        title.setText(text);
    }

    private void setState(ViewHolder holder, int state) {
        switch (state) {
            case STATE_CLOSED:
                holder.state.setClosed(true);
                holder.background.setImageAlpha(100);
                break;
            case STATE_OPENED:
                holder.state.setOpened(true);
                holder.background.setImageAlpha(100);
                break;
            case STATE_PASSED:
                holder.state.setPassed(true);
                holder.background.setImageAlpha(255);
                break;
            case STATE_FAILED:
                holder.state.setFailed(true);
                holder.background.setImageAlpha(255);
                break;
        }
    }

    private void setBackgroundImage(ImageView background, String imageUrl) {
        Picasso.with(background.getContext())
                .load(imageUrl)
                .into(background);
    }

    public void setListItems(List<StoryboardFrameListItem> listItems) {
        this.listItems = listItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.title) TextView title;
        @InjectView(R.id.state) QuestionStateImageButton state;
        @InjectView(R.id.background_view) ImageView background;

        private String key;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void setKey(String key) {
            this.key = key;
        }

        @OnClick(R.id.storyboard_frame_list_item)
        public void onStoryboardFrameListItemClicked() {
            bus.post(new StoryboardFrameListItemClicked(key));
        }

    }

}

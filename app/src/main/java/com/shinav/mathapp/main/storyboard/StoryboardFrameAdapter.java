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
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.TYPE_CONVERSATION;
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.TYPE_QUESTION;

public class StoryboardFrameAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_CONVERSATION = 0;
    public static final int VIEW_TYPE_QUESTION = 1;

    @Inject Bus bus;

    private List<StoryboardFrameListItem> listItems = Collections.emptyList();

    @Inject public StoryboardFrameAdapter() { }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        switch (viewType) {
            case VIEW_TYPE_QUESTION:
                view = inflater.inflate(R.layout.storyboard_list_item_question, parent, false);
                return new QuestionViewHolder(view);
            case VIEW_TYPE_CONVERSATION:
                view = inflater.inflate(R.layout.storyboard_list_item_conversation, parent, false);
                return new ConversationViewHolder(view);
        }

        return null;
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StoryboardFrameListItem listItem = listItems.get(position);

        switch (listItem.getType()) {
            case TYPE_QUESTION:
                QuestionViewHolder questionHolder = (QuestionViewHolder) holder;

                questionHolder.setKey(listItem.getKey());
                setTitle(questionHolder.title, listItem.getTitle());
                setBackgroundImage(questionHolder.background, listItem.getBackgroundImage());

                setQuestionState(questionHolder, listItem.getState());

                break;
            case TYPE_CONVERSATION:
                ConversationViewHolder conversationHolder = (ConversationViewHolder) holder;

                conversationHolder.setKey(listItem.getKey());
                setTitle(conversationHolder.title, listItem.getTitle());
                setBackgroundImage(conversationHolder.background, listItem.getBackgroundImage());

                setConversationState(conversationHolder, listItem.getState());
        }

    }

    @Override public int getItemCount() {
        return listItems.size();
    }

    @Override public int getItemViewType(int position) {
        StoryboardFrameListItem listItem = listItems.get(position);

        switch (listItem.getType()) {
            case TYPE_QUESTION:
                return VIEW_TYPE_QUESTION;
            case TYPE_CONVERSATION:
                return VIEW_TYPE_CONVERSATION;
        }

        return super.getItemViewType(position);
    }


    private void setTitle(TextView title, String text) {
        title.setText(text);
    }

    private void setQuestionState(QuestionViewHolder holder, int state) {
        switch (state) {
            case STATE_CLOSED:
                holder.state.setClosed(true);
                showBackgroundDarkened(holder.background);
                break;
            case STATE_OPENED:
                holder.state.setOpened(true);
                showBackgroundDarkened(holder.background);
                break;
            case STATE_PASSED:
                holder.state.setPassed(true);
                showBackgroundLightened(holder.background);
                break;
            case STATE_FAILED:
                holder.state.setFailed(true);
                showBackgroundLightened(holder.background);
                break;
        }
    }

    private void setConversationState(ConversationViewHolder holder, int state) {
        switch (state) {
            case STATE_CLOSED:
                holder.state.setClosed(true);
                showBackgroundDarkened(holder.background);
                break;
            case STATE_OPENED:
                holder.state.setOpened(true);
                showBackgroundLightened(holder.background);
                break;
        }
    }

    private void showBackgroundLightened(ImageView background) {
        background.setImageAlpha(255);
    }

    private void showBackgroundDarkened(ImageView background) {
        background.setImageAlpha(100);
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

    public class QuestionViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.title) TextView title;
        @InjectView(R.id.state) QuestionStateImageButton state;
        @InjectView(R.id.background_view) ImageView background;

        private String key;

        public QuestionViewHolder(View itemView) {
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

    public class ConversationViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.title) TextView title;
        @InjectView(R.id.state) ConversationStateImageButton state;
        @InjectView(R.id.background_view) ImageView background;

        private String key;

        public ConversationViewHolder(View itemView) {
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

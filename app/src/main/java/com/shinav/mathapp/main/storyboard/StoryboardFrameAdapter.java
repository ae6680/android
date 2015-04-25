package com.shinav.mathapp.main.storyboard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shinav.mathapp.R;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.STATE_CLOSED;
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.STATE_OPENED;
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.TYPE_CONVERSATION;
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.TYPE_QUESTION;

public class StoryboardFrameAdapter extends RecyclerView.Adapter<StoryboardFrameViewHolder> {

    public static final int VIEW_TYPE_CONVERSATION = 0;
    public static final int VIEW_TYPE_QUESTION = 1;

    private List<StoryboardFrameListItem> listItems = Collections.emptyList();
    private int conversationState = STATE_OPENED;

    @Inject public StoryboardFrameAdapter() { }

    @Override public StoryboardFrameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

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

    @Override public void onBindViewHolder(StoryboardFrameViewHolder holder, int position) {
        StoryboardFrameListItem listItem = listItems.get(position);

        holder.setKey(listItem.getKey());
        holder.setTitle(listItem.getTitle());
        holder.setBackgroundImage(listItem.getBackgroundImage());

        if (isFirstClosed(position, listItem.getState())) {
            holder.setState(STATE_OPENED);
            conversationState = STATE_CLOSED;
        } else {

            switch (listItem.getType()) {
                case TYPE_QUESTION:
                    holder.setState(listItem.getState());

                    if (listItem.getState() == STATE_OPENED) {
                        conversationState = STATE_CLOSED;
                    }

                    break;
                case TYPE_CONVERSATION:
                    holder.setState(conversationState);
            }

        }
    }

    private boolean isFirstClosed(int position, int state) {
        return position == 0 && state == STATE_CLOSED;
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

    public void setListItems(List<StoryboardFrameListItem> listItems) {
        this.listItems = listItems;
        conversationState = STATE_OPENED;
        notifyDataSetChanged();
    }

}

package com.shinav.mathapp.main.storyboard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shinav.mathapp.R;
import com.shinav.mathapp.main.storyboard.viewHolder.CutsceneViewHolder;
import com.shinav.mathapp.main.storyboard.viewHolder.QuestionViewHolder;
import com.shinav.mathapp.main.storyboard.viewHolder.StoryboardFrameViewHolder;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.STATE_OPENED;
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.TYPE_CUTSCENE;
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.TYPE_QUESTION;

public class StoryboardFrameAdapter extends RecyclerView.Adapter<StoryboardFrameViewHolder> {

    public static final int VIEW_TYPE_CUTSCENE = 0;
    public static final int VIEW_TYPE_QUESTION = 1;

    private List<StoryboardFrameListItem> listItems = Collections.emptyList();
    private int cutsceneState = STATE_OPENED;

    @Inject public StoryboardFrameAdapter() { }

    @Override public StoryboardFrameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        switch (viewType) {
            case VIEW_TYPE_QUESTION:
                view = inflater.inflate(R.layout.storyboard_frame_question, parent, false);
                return new QuestionViewHolder(view);
            case VIEW_TYPE_CUTSCENE:
                view = inflater.inflate(R.layout.storyboard_frame_cutscene, parent, false);
                return new CutsceneViewHolder(view);
        }

        return null;
    }

    @Override public void onBindViewHolder(StoryboardFrameViewHolder holder, int position) {
        StoryboardFrameListItem listItem = listItems.get(position);

        holder.setKey(listItem.getKey());
        holder.setTitle(listItem.getTitle());
        holder.setBackgroundImage(listItem.getBackgroundImage());

        holder.setState(STATE_OPENED);

//        switch (listItem.getType()) {
//            case TYPE_QUESTION:
//                holder.setState(listItem.getState());
//
//                // If it reaches the current open question,
//                // close the further cutscenes
//                if (listItem.getState() == STATE_OPENED) {
//                    cutsceneState = STATE_CLOSED;
//                }
//
//                break;
//            case TYPE_CUTSCENE:
//                holder.setState(cutsceneState);
//        }

    }

    @Override public int getItemCount() {
        return listItems.size();
    }

    @Override public int getItemViewType(int position) {
        StoryboardFrameListItem listItem = listItems.get(position);

        switch (listItem.getType()) {
            case TYPE_QUESTION:
                return VIEW_TYPE_QUESTION;
            case TYPE_CUTSCENE:
                return VIEW_TYPE_CUTSCENE;
        }

        return super.getItemViewType(position);
    }

    public void setListItems(List<StoryboardFrameListItem> listItems) {
        this.listItems = listItems;
        cutsceneState = STATE_OPENED;
        notifyDataSetChanged();
    }

}

package com.shinav.mathapp.main.storyProgress;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shinav.mathapp.R;
import com.shinav.mathapp.story.StoryEntry;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class StoryCardAdapter extends RecyclerView.Adapter<StoryCardAdapter.ViewHolder> {

    private List<StoryEntry> storyEntries = Collections.emptyList();

    @Inject
    public StoryCardAdapter() { }

    @Override
    public StoryCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.story_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(StoryCardAdapter.ViewHolder holder, int position) {

    }

    @Override public int getItemCount() {
        return storyEntries.size();
    }

    public void setStoryEntries(List<StoryEntry> storyEntries) {
        this.storyEntries = storyEntries;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}

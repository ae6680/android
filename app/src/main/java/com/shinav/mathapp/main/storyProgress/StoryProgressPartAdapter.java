package com.shinav.mathapp.main.storyProgress;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.pojo.StoryProgressPart;
import com.shinav.mathapp.event.MakeQuestionButtonClicked;
import com.shinav.mathapp.event.SeeQuestionButtonClicked;
import com.squareup.otto.Bus;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class StoryProgressPartAdapter extends RecyclerView.Adapter<StoryProgressPartAdapter.ViewHolder> {

    private final Bus bus;

    private List<StoryProgressPart> storyProgressParts = Collections.emptyList();

    @Inject
    public StoryProgressPartAdapter(Bus bus) {
        this.bus = bus;
    }

    @Override
    public StoryProgressPartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.story_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(StoryProgressPartAdapter.ViewHolder holder, int position) {
        StoryProgressPart storyProgressPart = storyProgressParts.get(position);

        holder.setQuestionKey(storyProgressPart.getQuestionKey());

        holder.title.setText(storyProgressPart.getTitle());

        switch (storyProgressPart.getState()) {
            case StoryProgressPart.STATE_UNMADE:
                holder.result.setText("Ongemaakt");
                holder.seeQuestionButton.setVisibility(GONE);
                break;
            case StoryProgressPart.STATE_PASS:
                holder.result.setText("Goed");
                holder.seeQuestionButton.setVisibility(VISIBLE);
                break;
            case StoryProgressPart.STATE_FAIL:
                holder.result.setText("Fout");
                holder.seeQuestionButton.setVisibility(GONE);
        }
    }

    @Override public int getItemCount() {
        return storyProgressParts.size();
    }

    public void setStoryProgressParts(List<StoryProgressPart> storyProgressParts) {
        this.storyProgressParts = storyProgressParts;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.question_title) TextView title;
        @InjectView(R.id.question_result) TextView result;
        @InjectView(R.id.see_question_button) Button seeQuestionButton;
        @InjectView(R.id.make_question_button) Button makeQuestionButton;

        private String questionKey;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void setQuestionKey(String questionKey) {
            this.questionKey = questionKey;
        }

        @OnClick(R.id.see_question_button)
        public void onSeeQuestionButtonClicked() {
            bus.post(new SeeQuestionButtonClicked(questionKey));
        }

        @OnClick(R.id.make_question_button)
        public void onMakeQuestionButtonClicked() {
            bus.post(new MakeQuestionButtonClicked(questionKey));
        }

    }

}

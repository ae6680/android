package com.shinav.mathapp.main.storyProgress;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.pojo.StoryboardFrame;
import com.shinav.mathapp.event.MakeQuestionButtonClicked;
import com.shinav.mathapp.event.SeeQuestionButtonClicked;
import com.squareup.otto.Bus;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class StoryboardFrameAdapter extends RecyclerView.Adapter<StoryboardFrameAdapter.ViewHolder> {

    @Inject Bus bus;

    private List<StoryboardFrame> storyboardFrames = Collections.emptyList();

    @Inject
    public StoryboardFrameAdapter() { }

    @Override
    public StoryboardFrameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.storyboard_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(StoryboardFrameAdapter.ViewHolder holder, int position) {
        StoryboardFrame storyboardFrame = storyboardFrames.get(position);

        holder.setQuestionKey(storyboardFrame.getFrameTypeKey());

        holder.title.setText(storyboardFrame.getFrameType());

//        switch (storyProgressPart.getState()) {
//            case StoryProgressPart.STATE_UNMADE:
//                holder.result.setText("Ongemaakt");
//                holder.seeQuestionButton.setVisibility(GONE);
//                break;
//            case StoryProgressPart.STATE_PASS:
//                holder.result.setText("Goed");
//                holder.seeQuestionButton.setVisibility(VISIBLE);
//                break;
//            case StoryProgressPart.STATE_FAIL:
//                holder.result.setText("Fout");
//                holder.seeQuestionButton.setVisibility(GONE);
//        }
    }

    @Override public int getItemCount() {
        return storyboardFrames.size();
    }

    public void setStoryboardFrames(List<StoryboardFrame> storyboardFrames) {
        this.storyboardFrames = storyboardFrames;
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

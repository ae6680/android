package com.shinav.mathapp.main.storyboard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shinav.mathapp.R;
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
import static com.shinav.mathapp.main.storyboard.StoryboardListItem.STATE_FAIL;
import static com.shinav.mathapp.main.storyboard.StoryboardListItem.STATE_PASS;
import static com.shinav.mathapp.main.storyboard.StoryboardListItem.STATE_UNMADE;

public class StoryboardFrameAdapter extends RecyclerView.Adapter<StoryboardFrameAdapter.ViewHolder> {

    @Inject Bus bus;

    private List<StoryboardListItem> listItems = Collections.emptyList();

    @Inject
    public StoryboardFrameAdapter() { }

    @Override
    public StoryboardFrameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.storyboard_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(StoryboardFrameAdapter.ViewHolder holder, int position) {
        StoryboardListItem listItem = listItems.get(position);

        holder.setQuestionKey(listItem.getQuestionKey());

        holder.title.setText(listItem.getQuestionTitle());
        holder.givenAnswer.setText(listItem.getLastGivenAnswer());

        switch (listItem.getState()) {
            case STATE_UNMADE:
                holder.state.setText("Ongemaakt");
                holder.seeQuestionButton.setVisibility(GONE);
                break;
            case STATE_PASS:
                holder.state.setText("Goed");
                holder.seeQuestionButton.setVisibility(VISIBLE);
                break;
            case STATE_FAIL:
                holder.state.setText("Fout");
                holder.seeQuestionButton.setVisibility(GONE);
        }
    }

    @Override public int getItemCount() {
        return listItems.size();
    }

    public void setListItems(List<StoryboardListItem> listItems) {
        this.listItems = listItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.question_title) TextView title;
        @InjectView(R.id.question_state) TextView state;
        @InjectView(R.id.question_given_answer) TextView givenAnswer;
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

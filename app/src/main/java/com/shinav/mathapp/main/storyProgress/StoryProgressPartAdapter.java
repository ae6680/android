package com.shinav.mathapp.main.storyProgress;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.event.MakeQuestionButtonClicked;
import com.shinav.mathapp.event.SeeQuestionButtonClicked;
import com.shinav.mathapp.question.Question;
import com.squareup.otto.Bus;
import com.squareup.sqlbrite.SqlBrite;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class StoryProgressPartAdapter extends RecyclerView.Adapter<StoryProgressPartAdapter.ViewHolder> {

    private final Bus bus;
    private final SqlBrite db;
    private List<StoryProgressPart> storyProgressParts = Collections.emptyList();

    public StoryProgressPartAdapter(Bus bus, SqlBrite db) {
        this.bus = bus;
        this.db = db;
    }

    @Override
    public StoryProgressPartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.story_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(StoryProgressPartAdapter.ViewHolder holder, int position) {
        StoryProgressPart storyProgressPart = storyProgressParts.get(position);

        if (!isSameQuestion(holder, storyProgressPart)) {

            Cursor c = db.query(
                    "SELECT * FROM " + Tables.Question.TABLE_NAME +
                            " WHERE " + Tables.Question.KEY + " = ?"
                    , storyProgressPart.getQuestionKey()
            );

            if (c.moveToFirst()) {
                holder.question = Question.fromCursor(c);
            }
        }

        if (holder.question != null) {
            holder.title.setText(holder.question.getTitle());

            if (TextUtils.isEmpty(storyProgressPart.getGivenAnswer())) {

                holder.result.setText("Ongemaakt");
                holder.seeQuestionButton.setVisibility(GONE);

            } else {

                if (isMadeCorrect(storyProgressPart, holder.question)) {
                    holder.seeQuestionButton.setVisibility(VISIBLE);
                    holder.result.setText("Goed");
                } else {
                    holder.seeQuestionButton.setVisibility(GONE);
                    holder.result.setText("Fout");
                }

            }
        }
    }

    private boolean isSameQuestion(ViewHolder holder, StoryProgressPart storyProgressPart) {
        return holder.question != null && holder.question.getKey().equals(storyProgressPart.getQuestionKey());
    }

    private boolean isMadeCorrect(StoryProgressPart storyProgressPart, Question question) {
        return storyProgressPart.getGivenAnswer().equals(question.getAnswer());
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

        private Question question;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void setQuestion(Question question) {
            this.question = question;
        }

        @OnClick(R.id.see_question_button)
        public void onSeeQuestionButtonClicked() {
            bus.post(new SeeQuestionButtonClicked(question.getKey()));
        }

        @OnClick(R.id.make_question_button)
        public void onMakeQuestionButtonClicked() {
            bus.post(new MakeQuestionButtonClicked(question.getKey()));
        }

    }

}

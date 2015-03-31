package com.shinav.mathapp.main.storyProgress;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.event.MakeQuestionButtonClicked;
import com.shinav.mathapp.event.SeeQuestionButtonClicked;
import com.shinav.mathapp.question.Question;
import com.squareup.otto.Bus;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class StoryQuestionCardAdapter extends RecyclerView.Adapter<StoryQuestionCardAdapter.ViewHolder> {

    private List<Question> questions = Collections.emptyList();
    private final Bus bus;

    public StoryQuestionCardAdapter(Bus bus) {
        this.bus = bus;
    }

    @Override
    public StoryQuestionCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.story_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(StoryQuestionCardAdapter.ViewHolder holder, int position) {
        final Question question = questions.get(position);

        if (question != null) {
            holder.title.setText(question.getTitle());

            holder.setQuestion(question);
        }
    }

    @Override public int getItemCount() {
        return questions.size();
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.question_title) TextView title;
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
            bus.post(new SeeQuestionButtonClicked(question.getFirebaseKey()));
        }

        @OnClick(R.id.make_question_button)
        public void onMakeQuestionButtonClicked() {
            bus.post(new MakeQuestionButtonClicked(question.getFirebaseKey()));
        }

    }

}

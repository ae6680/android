package com.shinav.mathapp.questionApproach.feedback;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.pojo.QuestionApproachPart;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class QuestionApproachPartFeedbackAdapter extends RecyclerView.Adapter<QuestionApproachPartFeedbackAdapter.ViewHolder> {

    List<QuestionApproachPart> questionApproachParts = Collections.emptyList();

    @Inject
    public QuestionApproachPartFeedbackAdapter() { }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.approach_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        holder.number.setText(String.valueOf(position+1));
        holder.text.setText(getItem(position).getValue());

        View background = holder.itemView.findViewById(R.id.approach_list_item);

        Resources res = background.getContext().getResources();

        if (isCorrect(position)) {
            background.setBackgroundColor(res.getColor(R.color.input_correct));
        } else {
            background.setBackgroundColor(res.getColor(R.color.input_incorrect));
        }
    }

    public QuestionApproachPart getItem(int position) {
        return questionApproachParts.get(position);
    }

    @Override public int getItemCount() {
        return questionApproachParts.size();
    }

    public void setQuestionApproachParts(List<QuestionApproachPart> questionApproachParts) {
        this.questionApproachParts = questionApproachParts;
        notifyDataSetChanged();
    }

    public List<QuestionApproachPart> getQuestionApproachParts() {
        return questionApproachParts;
    }

    public boolean isCorrect(int position) {
        return position == getItem(position).getPosition();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.approach_part_number) TextView number;
        @InjectView(R.id.approach_part_text) TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}

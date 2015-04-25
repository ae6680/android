package com.shinav.mathapp.questionApproachFeedback;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.pojo.QuestionApproachPart;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class QAFViewPagerPageAdapter extends RecyclerView.Adapter<QAFViewPagerPageAdapter.ViewHolder> {

    List<QuestionApproachPart> questionApproachParts = Collections.emptyList();

    @Inject
    public QAFViewPagerPageAdapter() { }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_approach_feedback_adapter_item, parent, false);

        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {

        QuestionApproachPart item = getItem(position);

        holder.text.setText(item.getValue());

        if (isCorrect(item)) {
            holder.resultIcon.setImageResource(R.drawable.pass_icon);
        } else {
            holder.resultIcon.setImageResource(R.drawable.fail_icon);
        }

    }

    @Override public int getItemCount() {
        return questionApproachParts.size();
    }

    public QuestionApproachPart getItem(int position) {
        return questionApproachParts.get(position);
    }

    public void setQuestionApproachParts(List<QuestionApproachPart> questionApproachParts) {
        this.questionApproachParts = questionApproachParts;
        notifyDataSetChanged();
    }

    public boolean isCorrect(QuestionApproachPart part) {
        // If position is same as the last(correct) part.
        return part.getPosition() == getItem(questionApproachParts.size() - 1).getPosition();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.question_approach_part_text) TextView text;
        @InjectView(R.id.result_icon) ImageView resultIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}

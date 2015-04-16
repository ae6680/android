package com.shinav.mathapp.questionApproach;

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

public class QuestionApproachPartAdapter extends RecyclerView.Adapter<QuestionApproachPartAdapter.ViewHolder> {

    List<QuestionApproachPart> questionApproachParts = Collections.emptyList();

    @Inject
    public QuestionApproachPartAdapter() { }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.approach_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        holder.number.setText(String.valueOf(position+1));
        holder.text.setText(getItem(position).getValue());
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.approach_part_number) TextView number;
        @InjectView(R.id.approach_part_text) TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}

package com.shinav.mathapp.approach.feedback;

import android.content.res.Resources;
import android.view.View;

import com.shinav.mathapp.R;
import com.shinav.mathapp.approach.ApproachEntryAdapter;

import javax.inject.Inject;

public class ApproachEntryFeedbackAdapter extends ApproachEntryAdapter {

    @Inject
    public ApproachEntryFeedbackAdapter() { }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {

        View background = holder.itemView.findViewById(R.id.approach_list_item);

        Resources res = background.getContext().getResources();

        if (isCorrect(position)) {
            background.setBackgroundColor(res.getColor(R.color.input_correct));
        } else {
            background.setBackgroundColor(res.getColor(R.color.input_incorrect));
        }

        super.onBindViewHolder(holder, position);
    }

    public boolean isCorrect(int position) {
        return position == super.getItem(position).getPosition();
    }
}

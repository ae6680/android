package com.shinav.mathapp.approach;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.model.ApproachPart;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ApproachPartAdapter extends RecyclerView.Adapter<ApproachPartAdapter.ViewHolder> {

    List<ApproachPart> approachParts = Collections.emptyList();

    @Inject
    public ApproachPartAdapter() { }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.approach_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        holder.number.setText(String.valueOf(position+1));
        holder.text.setText(getItem(position).getValue());
    }

    public ApproachPart getItem(int position) {
        return approachParts.get(position);
    }

    @Override public int getItemCount() {
        return approachParts.size();
    }

    public void setApproachParts(List<ApproachPart> approachParts) {
        this.approachParts = approachParts;
        notifyDataSetChanged();
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

package com.shinav.mathapp.approach;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shinav.mathapp.R;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ApproachAdapter extends RecyclerView.Adapter<ApproachAdapter.ViewHolder> {

    List<Approach> approaches = Collections.emptyList();

    @Inject
    public ApproachAdapter() { }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.approach_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        holder.number.setText(String.valueOf(position+1));
        holder.text.setText(getItem(position).getText());
    }

    public Approach getItem(int position) {
        return approaches.get(position);
    }

    @Override public int getItemCount() {
        return approaches.size();
    }

    public void setApproaches(List<Approach> approaches) {
        this.approaches = approaches;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.approach_number) TextView number;
        @InjectView(R.id.approach_text) TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}

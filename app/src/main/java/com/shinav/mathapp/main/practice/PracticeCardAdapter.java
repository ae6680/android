package com.shinav.mathapp.main.practice;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shinav.mathapp.R;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class PracticeCardAdapter extends RecyclerView.Adapter<PracticeCardAdapter.ViewHolder> {

    @Inject
    public PracticeCardAdapter() { }

    @Override
    public PracticeCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.practice_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(PracticeCardAdapter.ViewHolder holder, int position) {

    }

    @Override public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}

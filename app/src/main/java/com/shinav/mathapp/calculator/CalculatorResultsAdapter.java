package com.shinav.mathapp.calculator;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shinav.mathapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CalculatorResultsAdapter extends RecyclerView.Adapter<CalculatorResultsAdapter.ViewHolder> {

    private List<CalculatorResult> results = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.calculator_results_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        CalculatorResult result = getItem(i);

        viewHolder.calculation.setText(result.calculation);
        viewHolder.answer.setText(result.answer);
    }

    private CalculatorResult getItem(int position) {
        return results.get(position);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void addItem(CalculatorResult result) {
        this.results.add(result);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.results_calculation) TextView calculation;
        @InjectView(R.id.results_calculation_answer) TextView answer;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

    }
}

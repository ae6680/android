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

    private List<CalculatorEntry> results = new ArrayList<>();

    public CalculatorResultsAdapter() {
        populate();
    }

    private void populate() {
        for (int i = 0; i < 2; i++) {
            results.add(new CalculatorEntry());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.calculator_results_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        CalculatorEntry result = getItem(i);

        viewHolder.equation.setText(result.equation);
        viewHolder.answer.setText(result.answer);
    }

    private CalculatorEntry getItem(int position) {
        return results.get(position);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void addItem(CalculatorEntry result) {
        this.results.add(result);
        notifyDataSetChanged();
    }

    public void updateLastItem(CalculatorEntry entry) {
        if (results.size() > 0) {
            CalculatorEntry lastItem = getItem(results.size() - 2);
            lastItem.answer = entry.answer;
            lastItem.equation = entry.equation;

            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.results_equation) TextView equation;
        @InjectView(R.id.results_equation_answer) TextView answer;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

    }
}

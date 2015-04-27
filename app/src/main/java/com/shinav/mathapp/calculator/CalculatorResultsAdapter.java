package com.shinav.mathapp.calculator;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.event.CalculatorResultAreaClickedEvent;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CalculatorResultsAdapter extends RecyclerView.Adapter<CalculatorResultsAdapter.ViewHolder> {

    public static final int ITEM_LAYOUT = R.layout.calculator_results_item;

    private List<CalculatorResultItem> results = new ArrayList<>();
    private View lastVisibleItemView;

    private final Bus bus;

    @Inject
    public CalculatorResultsAdapter(Bus bus) {
        this.bus = bus;
        populate();
    }

    private void populate() {
        for (int i = 0; i < 2; i++) {
            results.add(new CalculatorResultItem());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(ITEM_LAYOUT, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        CalculatorResultItem result = getItem(i);

        viewHolder.equation.setText(result.equation);
        viewHolder.answer.setText(result.answer);

        if (results.size()-2 == i) {
            lastVisibleItemView = viewHolder.itemView;
        }
    }

    private CalculatorResultItem getItem(int position) {
        return results.get(position);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void addItem(CalculatorResultItem result) {
        this.results.add(result);
        notifyDataSetChanged();
    }

    public void updateLastItem(CalculatorResultItem item) {
        if (results.size() > 0) {
            CalculatorResultItem lastItem = getLastItem();
            lastItem.answer = item.answer;
            lastItem.equation = item.equation;

            notifyDataSetChanged();
        }
    }

    private CalculatorResultItem getLastItem() {
        return getItem(results.size() - 2);
    }

    public void blinkLastItem() {
        final Handler handler = new Handler();
        new Runnable() {
            public int counter = 0;

            @Override public void run() {
                if (counter % 2 == 0) {
                    lastVisibleItemView.setBackgroundResource(R.color.calculator_results_background);
                } else {
                    lastVisibleItemView.setBackgroundResource(R.color.calculator_results_blink);
                }

                counter++;
                if (counter < 5) {
                    handler.postDelayed(this, 200);
                }
            }
        }.run();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @InjectView(R.id.results_equation) TextView equation;
        @InjectView(R.id.results_equation_answer) TextView answer;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override public void onClick(View v) {
            bus.post(new CalculatorResultAreaClickedEvent());
        }
    }
}

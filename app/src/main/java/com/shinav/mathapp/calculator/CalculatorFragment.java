package com.shinav.mathapp.calculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shinav.mathapp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CalculatorFragment extends Fragment {

    @InjectView(R.id.numpad_number_0) TextView numpadNumber0;
    @InjectView(R.id.numpad_number_1) TextView numpadNumber1;
    @InjectView(R.id.numpad_number_2) TextView numpadNumber2;
    @InjectView(R.id.numpad_number_3) TextView numpadNumber3;
    @InjectView(R.id.numpad_number_4) TextView numpadNumber4;
    @InjectView(R.id.numpad_number_5) TextView numpadNumber5;
    @InjectView(R.id.numpad_number_6) TextView numpadNumber6;
    @InjectView(R.id.numpad_number_7) TextView numpadNumber7;
    @InjectView(R.id.numpad_number_8) TextView numpadNumber8;
    @InjectView(R.id.numpad_number_9) TextView numpadNumber9;

    @InjectView(R.id.calculator_results) RecyclerView calculatorResults;

    private String calculation = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.calculator, container, false);
        ButterKnife.inject(this, view);

        setNumpadNumberClickListeners();

        initResults();

        return view;
    }

    private void setNumpadNumberClickListeners() {
        numpadNumber0.setOnClickListener(numpadNumberClickListerner);
        numpadNumber1.setOnClickListener(numpadNumberClickListerner);
        numpadNumber2.setOnClickListener(numpadNumberClickListerner);
        numpadNumber3.setOnClickListener(numpadNumberClickListerner);
        numpadNumber4.setOnClickListener(numpadNumberClickListerner);
        numpadNumber5.setOnClickListener(numpadNumberClickListerner);
        numpadNumber6.setOnClickListener(numpadNumberClickListerner);
        numpadNumber7.setOnClickListener(numpadNumberClickListerner);
        numpadNumber8.setOnClickListener(numpadNumberClickListerner);
        numpadNumber9.setOnClickListener(numpadNumberClickListerner);
    }

    public void initResults() {
        calculatorResults.setLayoutManager(new LinearLayoutManager(getActivity()));
        calculatorResults.setAdapter(new CalculatorResultsAdapter());
    }

    public void numberClicked(String text) {
        calculation += text;
    }

    @OnClick(R.id.calculator_options_equals)
    public void onEqualsClicked() {
        showResult();
        scrollToLast();
    }

    private void scrollToLast() {
        int amount = calculatorResults.getAdapter().getItemCount();
        calculatorResults.scrollToPosition(amount-1);
    }

    private void showResult() {

        if (!TextUtils.isEmpty(calculation)) {
            CalculatorResult result = new CalculatorResult();
            result.answer = calculation;
            result.calculation = calculation;

            ((CalculatorResultsAdapter) calculatorResults.getAdapter()).addItem(result);

            calculation = "";
        }

    }

    View.OnClickListener numpadNumberClickListerner = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            TextView tv = (TextView) v;
            String text = tv.getText().toString();

            numberClicked(text);
        }
    };

}

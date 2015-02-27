package com.shinav.mathapp.calculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.bus.BusProvider;
import com.shinav.mathapp.event.OnAnswerChangedEvent;
import com.shinav.mathapp.question.QuestionFragment;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class CalculatorFragment extends Fragment {

    private static final String TAG = "CalculatorFragment";
    @InjectView(R.id.calculator_results) RecyclerView calculatorResults;

    @InjectViews({
            R.id.numpad_number_0,
            R.id.numpad_number_1,
            R.id.numpad_number_2,
            R.id.numpad_number_3,
            R.id.numpad_number_4,
            R.id.numpad_number_5,
            R.id.numpad_number_6,
            R.id.numpad_number_7,
            R.id.numpad_number_8,
            R.id.numpad_number_9
    }) List<TextView> numpadNumberViews;

    @InjectViews({
            R.id.calculator_options_square_root,
            R.id.calculator_options_divide,
            R.id.calculator_options_subtraction,
            R.id.calculator_options_power_of,
            R.id.calculator_options_multiply,
            R.id.calculator_options_addition
    }) List<TextView> numpadOptionViews;

    private String calculationString = "";
    private String answer = "";
    private CalculatorResultsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.calculator, container, false);
        ButterKnife.inject(this, view);

        setNumpadNumberClickListeners();
        setNumpadOptionClickListeners();

        initResults();

        return view;
    }

    private void setNumpadOptionClickListeners() {
        for (View view : numpadOptionViews) {
            view.setOnClickListener(numpadOptionClickListerner);
        }
    }

    private void setNumpadNumberClickListeners() {
        for (View view : numpadNumberViews) {
            view.setOnClickListener(numpadNumberClickListerner);
        }
    }

    public void initResults() {
        adapter = new CalculatorResultsAdapter();
        calculatorResults.setLayoutManager(new LinearLayoutManager(getActivity()));
        calculatorResults.setAdapter(adapter);

        // Populate
        for (int i = 0; i < 3; i++) {
            CalculatorEntry calculatorEntry = new CalculatorEntry();
            calculatorEntry.answer = "";
            adapter.addItem(calculatorEntry);
        }
    }

    public void numberClicked(String text) {
        calculationString += text;
        calculate();
        showResult();
    }

    private void calculate() {
        try {
            String filteredCalculationString = filterCalculation();
            Expression expression = new ExpressionBuilder(filteredCalculationString).build();
            String ans = String.valueOf(expression.evaluate());

            String dotSplit = ans.split("\\.")[1];
            int decimalRange = ans.length() - dotSplit.length() + 3;
            if (decimalRange < ans.length()) {
                ans = ans.substring(0, decimalRange);
            }

            if (ans.endsWith(".0")) {
                ans = ans.substring(0, ans.length() - 2);
            }

            answer = ans.replace(".", ",");

        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
            answer = "";
        }
    }

    private String filterCalculation() {
        String validCalculation = calculationString
                .replace("×", "*")
                .replace("÷", "/")
                .replace(",", ".");

        int squareIndex = validCalculation.indexOf("√");
        // Check for square root in calculation.
        if (squareIndex != -1) {

            String sub = validCalculation.substring(squareIndex+1);

            String[] splits = sub.split(" ");

            String numberBehindSquare = "";
            if (splits.length > 0) {
                numberBehindSquare = splits[1];
            }

            if (numberBehindSquare.isEmpty()) {
                numberBehindSquare = sub;
            }

            String newReplace = "sqrt(" + numberBehindSquare + ")";
            String oldReplace = "√ " + numberBehindSquare;

            validCalculation = validCalculation.replace(oldReplace, newReplace);
        }

        return validCalculation;
    }

    private void optionClicked(String text) {

        if (!TextUtils.isEmpty(answer) && TextUtils.isEmpty(calculationString)) {
            calculationString += answer;
        }

        String addedText = " " + text + " ";

        if (!calculationString.endsWith("√")) {
            calculationString += addedText;
        }

        showResult();
    }

    @OnClick(R.id.calculator_options_equals)
    public void onEqualsClicked() {
        if (!TextUtils.isEmpty(calculationString)) {
            adapter.addItem(new CalculatorEntry());
        }
        calculationString = "";
        scrollToLast();
    }

    @OnClick(R.id.numpad_backspace)
    public void onBackspace() {
        if (calculationString.length() > 0) {

            int amountToRemove = calculationString.endsWith(" ") ? 3 : 1;

            calculationString = calculationString.substring(0, calculationString.length() - amountToRemove);

            calculate();
            showResult();

        }
    }

    @OnLongClick(R.id.numpad_backspace)
    public boolean onLongBackspace() {
        calculationString = "";
        answer = "";
        showResult();

        return false;
    }

    @OnClick(R.id.numpad_comma)
    public void onComma() {
        calculationString += ",";
        showResult();
    }

    @OnClick(R.id.calculator_options_parenthesis)
    public void onParenthesis() {
        int openCounter = StringUtils.countMatches(calculationString, "(");
        int closeCounter = StringUtils.countMatches(calculationString, ")");

        calculationString += openCounter == closeCounter ? "(" : ")";

        calculate();
        showResult();
    }

    private void scrollToLast() {
        int amount = calculatorResults.getAdapter().getItemCount();
        calculatorResults.scrollToPosition(amount-1);
    }

    private void showResult() {

        CalculatorEntry calculatorEntry = new CalculatorEntry();
        calculatorEntry.answer = answer;
        calculatorEntry.calculation = calculationString;

        adapter.updateLastItem(calculatorEntry);

        scrollToLast();

        ((QuestionFragment) getParentFragment()).onAnswerChangedEvent(answer);
    }

    View.OnClickListener numpadNumberClickListerner = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String text = ((TextView) v).getText().toString();
            numberClicked(text);
        }
    };

    View.OnClickListener numpadOptionClickListerner = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String text = ((TextView) v).getText().toString();
            optionClicked(text);
        }
    };

}

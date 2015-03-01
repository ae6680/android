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
import com.shinav.mathapp.question.QuestionFragment;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class CalculatorFragment extends Fragment implements CalculatorView {

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
    private CalculatorResultsAdapter resultsAdapter;
    private Calculator calculator;
    private CalculatorPresenter calculatorPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calculator, container, false);

        ButterKnife.inject(this, view);

        calculator = new Calculator();
        calculatorPresenter = new CalculatorPresenter(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        calculatorPresenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        calculatorPresenter.stop();
    }

    public void setNumpadOptionClickListeners() {
        for (View view : numpadOptionViews) {
            view.setOnClickListener(numpadOptionClickListener);
        }
    }

    public void setNumpadNumberClickListeners() {
        for (View view : numpadNumberViews) {
            view.setOnClickListener(numpadNumberClickListener);
        }
    }

    public void showCalculatorResultArea() {
        resultsAdapter = new CalculatorResultsAdapter();
        calculatorResults.setLayoutManager(new LinearLayoutManager(getActivity()));
        calculatorResults.setAdapter(resultsAdapter);
    }

    private void numberClicked(String text) {
        calculationString += text;
        answer = calculator.calculate(calculationString);
        updateLastCalculatorEntry();
    }

    private void optionClicked(String text) {

        checkForResumedCalculating();

        String addedText = " " + text + " ";

        if (!calculationString.endsWith("√")) {
            calculationString += addedText;
        }

        updateLastCalculatorEntry();
    }

    private void checkForResumedCalculating() {
        if (!TextUtils.isEmpty(answer) && TextUtils.isEmpty(calculationString)) {
            calculationString += answer;
        }
    }

    @OnClick(R.id.calculator_options_equals)
    public void onEqualsClicked() {
        if (!TextUtils.isEmpty(calculationString)) {
            resultsAdapter.addItem(new CalculatorEntry());
        }
        calculationString = "";
        scrollToLast();
    }

    @OnClick(R.id.numpad_backspace)
    public void onBackspace() {
        if (calculationString.length() > 0) {

            int amountToRemove = calculationString.endsWith(" ") ? 3 : 1;

            calculationString = calculationString.substring(0, calculationString.length() - amountToRemove);

            answer = calculator.calculate(calculationString);
            updateLastCalculatorEntry();
        }
    }

    @OnLongClick(R.id.numpad_backspace)
    public boolean onLongBackspace() {
        calculationString = "";
        answer = "";
        updateLastCalculatorEntry();

        return false;
    }

    @OnClick(R.id.numpad_comma)
    public void onComma() {
        calculationString += ",";
        updateLastCalculatorEntry();
    }

    @OnClick(R.id.calculator_options_parenthesis)
    public void onParenthesis() {
        int openCounter = StringUtils.countMatches(calculationString, "(");
        int closeCounter = StringUtils.countMatches(calculationString, ")");

        if (openCounter == closeCounter) {
            calculationString += "(";
        } else {
            if (!calculationString.endsWith("(")) {
                calculationString += ")";
                answer = calculator.calculate(calculationString);
            }
        }

        updateLastCalculatorEntry();
    }

    private void scrollToLast() {
        int amount = calculatorResults.getAdapter().getItemCount();
        calculatorResults.scrollToPosition(amount-1);
    }

    private void updateLastCalculatorEntry() {

        CalculatorEntry calculatorEntry = new CalculatorEntry();
        calculatorEntry.answer = answer;
        calculatorEntry.calculation = calculationString;

        resultsAdapter.updateLastItem(calculatorEntry);

        scrollToLast();

        ((QuestionFragment) getParentFragment()).onAnswerChanged(answer);
    }

    View.OnClickListener numpadNumberClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String text = ((TextView) v).getText().toString();
            numberClicked(text);
        }
    };

    View.OnClickListener numpadOptionClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String text = ((TextView) v).getText().toString();
            optionClicked(text);
        }
    };

}

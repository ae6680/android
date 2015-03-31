package com.shinav.mathapp.calculator;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.event.OnNumpadOperationClickedEvent;
import com.shinav.mathapp.injection.InjectedFragment;
import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class CalculatorFragment extends InjectedFragment {

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
    }) List<TextView> numpadOperatorViews;

    @Inject Calculator calculator;
    @Inject EquationHandler equationHandler;
    @Inject CalculatorResultsAdapter resultsAdapter;
    @Inject Bus bus;

    private String equation = "";
    private String answer = "";
    private boolean hasFocus = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calculator, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override public void onStart() {
        super.onStart();

        setNumpadNumberClickListeners();
        setNumpadOperatorClickListeners();
        showCalculatorResultArea();
    }

    private void setNumpadOperatorClickListeners() {
        for (View view : numpadOperatorViews) {
            view.setOnClickListener(numpadOperatorClickListener);
        }
    }

    private void setNumpadNumberClickListeners() {
        for (View view : numpadNumberViews) {
            view.setOnClickListener(numpadNumberClickListener);
        }
    }

    private void showCalculatorResultArea() {
        calculatorResults.setLayoutManager(new LinearLayoutManager(getActivity()));
        calculatorResults.setAdapter(resultsAdapter);
    }

    private void numberClicked(String number) {
        if (hasFocus) {
            equation = equationHandler.handleNumber(equation, number);
            answer = calculator.calculate(equation);
            updateLastCalculatorResultItem();
        } else {
            bus.post(new OnNumpadOperationClickedEvent(
                    OnNumpadOperationClickedEvent.OPERATION_INSERT, number));
        }
    }

    private void operatorClicked(String operator) {
        if (hasFocus) {
            checkForResumedCalculating();

            if (!equation.endsWith("√")) {
                equation = equationHandler.handleOperator(equation, operator);
            }

            updateLastCalculatorResultItem();
        }
    }

    private void checkForResumedCalculating() {
        if (!TextUtils.isEmpty(answer) && TextUtils.isEmpty(equation)) {
            equation = equationHandler.handleResumedCalculating(equation, answer);
        }
    }

    @OnClick(R.id.calculator_options_equals)
    public void onEqualsClicked() {
        if (!TextUtils.isEmpty(equation)) {
            resultsAdapter.addItem(new CalculatorResultItem());
        }
        equation = "";
        scrollToLast();
    }

    @OnClick(R.id.numpad_backspace)
    public void onBackspace() {
        if (hasFocus) {
            if (equation.length() > 0) {
                equation = equationHandler.handleBackspace(equation);

                answer = calculator.calculate(equation);
                updateLastCalculatorResultItem();
            }
        } else {
            bus.post(new OnNumpadOperationClickedEvent(
                    OnNumpadOperationClickedEvent.OPERATION_BACKSPACE, null));
        }
    }

    @OnLongClick(R.id.numpad_backspace)
    public boolean onLongBackspace() {
        if (hasFocus) {
            equation = "";
            answer = "";
            updateLastCalculatorResultItem();
        } else {
            bus.post(new OnNumpadOperationClickedEvent(
                    OnNumpadOperationClickedEvent.OPERATION_REMOVE_ALL, null));
        }

        return false;
    }

    @OnClick(R.id.numpad_comma)
    public void onComma() {
        if (hasFocus) {
            equation += ",";
            updateLastCalculatorResultItem();
        } else {
            bus.post(new OnNumpadOperationClickedEvent(
                    OnNumpadOperationClickedEvent.OPERATION_INSERT, ","));
        }
    }

    @OnClick(R.id.calculator_options_parenthesis)
    public void onParenthesis() {

        equation += equationHandler.handleParenthesis(equation);

        if (equation.endsWith(")")) {
            answer = calculator.calculate(equation);
        }

        updateLastCalculatorResultItem();
    }

    private void scrollToLast() {
        int amount = calculatorResults.getAdapter().getItemCount();
        calculatorResults.scrollToPosition(amount - 1);
    }

    private void updateLastCalculatorResultItem() {

        CalculatorResultItem calculatorResultItem = new CalculatorResultItem();
        calculatorResultItem.answer = answer;
        calculatorResultItem.equation = equation;

        resultsAdapter.updateLastItem(calculatorResultItem);

        scrollToLast();
    }

    View.OnClickListener numpadNumberClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String text = ((TextView) v).getText().toString();
            numberClicked(text);
        }
    };

    View.OnClickListener numpadOperatorClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String text = ((TextView) v).getText().toString();
            operatorClicked(text);
        }
    };

    public void gainFocus() {
        if (!hasFocus) {
            resultsAdapter.blinkLastItem();
        }
        hasFocus = true;
    }

    public void releaseFocus() {
        hasFocus = false;
    }
}

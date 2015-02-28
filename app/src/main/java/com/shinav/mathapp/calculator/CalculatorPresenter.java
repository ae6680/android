package com.shinav.mathapp.calculator;

import com.shinav.mathapp.presenter.Presenter;

public class CalculatorPresenter extends Presenter {

    private final CalculatorView calculatorView;

    public CalculatorPresenter(CalculatorView calculatorView) {
        this.calculatorView = calculatorView;
    }

    @Override
    public void start() {
        calculatorView.setNumpadNumberClickListeners();
        calculatorView.setNumpadOptionClickListeners();
        calculatorView.showCalculatorResultArea();
    }

    @Override
    public void stop() {

    }

}

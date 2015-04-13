package com.shinav.mathapp.calculator;

import android.util.Log;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import javax.inject.Inject;

public class Calculator {

    private static final String TAG = "Calculator";

    private EquationFilterer equationFilterer;
    private AnswerCleaner answerCleaner;

    @Inject
    public Calculator(EquationFilterer equationFilterer, AnswerCleaner answerCleaner) {
        this.equationFilterer = equationFilterer;
        this.answerCleaner = answerCleaner;
    }

    public String calculate(String equation) {
        String answer = "";

        try {
            String filteredEquation = equationFilterer.filterEquation(equation);

            String rawAnswer = calculateEquation(filteredEquation);

            answer = answerCleaner.clean(rawAnswer);

        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
        }

        return answer;
    }

    private String calculateEquation(String equation) {
        Expression expression = new ExpressionBuilder(equation).build();
        try {
            return String.valueOf(expression.evaluate());
        } catch (ArithmeticException e) {
            // Division by zero!
            e.printStackTrace();
            return "";
        }
    }

}

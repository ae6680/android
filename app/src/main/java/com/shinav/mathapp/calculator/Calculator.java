package com.shinav.mathapp.calculator;

import android.util.Log;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import javax.inject.Inject;

public class Calculator {

    private static final String TAG = "Calculator";
    private static final int AMOUNT_OF_DECIMALS = 5;

    @Inject EquationFilterer equationFilterer;

    public String calculate(String equation) {
        String answer = "";

        try {
            String filteredEquation = equationFilterer.filterEquation(equation);

            String rawAnswer = calculateString(filteredEquation);

            answer = cleanUpAnswer(rawAnswer);

        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
        }

        return answer;
    }

    private String calculateString(String equation) {
        Expression expression = new ExpressionBuilder(equation).build();
        String answer = String.valueOf(expression.evaluate());
        return answer;
    }

    private String cleanUpAnswer(String answer) {
        String roundOfAnswer = roundOnDecimal(answer, AMOUNT_OF_DECIMALS);
        String strippedAnswer = stripDotZero(roundOfAnswer);
        String cleanAnswer = strippedAnswer.replace(".", ",");

        return cleanAnswer;
    }

    private String roundOnDecimal(String answer, int amountOfDecimals) {

        String[] splits = answer.split("\\.");
        if (splits.length > 0) {
            String dotSplit = splits[1];
            int decimalRange = answer.length() - dotSplit.length() + amountOfDecimals;
            if (decimalRange < answer.length()) {
                answer = answer.substring(0, decimalRange);
            }
        }

        return answer;
    }

    private String stripDotZero(String answer) {
        if (answer.endsWith(".0")) {
            answer = answer.substring(0, answer.length() - 2);
        }
        return answer;
    }

}

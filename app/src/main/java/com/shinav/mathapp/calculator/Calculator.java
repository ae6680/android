package com.shinav.mathapp.calculator;

import android.util.Log;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Calculator {

    private static final String TAG = "Calculator";
    private static final int AMOUNT_OF_DECIMALS = 3;

    public String calculate(String calculation) {
        String answer = "";

        try {
            String filteredCalculation = filterCalculation(calculation);

            String rawAnswer = calculateString(filteredCalculation);

            answer = cleanUpAnswer(rawAnswer);

        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
        }

        return answer;
    }

    private String filterCalculation(String calculation) {
        String filteredCalculation = calculation
                .replace("×", "*")
                .replace("÷", "/")
                .replace(",", ".");

        String validCalculation = filterSquareRoot(filteredCalculation);

        return validCalculation;
    }

    private String filterSquareRoot(String calculation) {
        int squareIndex = calculation.indexOf("√");
        if (squareIndex != -1) {

            String subString = calculation.substring(squareIndex+1);

            String[] splits = subString.split(" ");

            String numberBehindSquare = "";
            if (splits.length > 0) {
                numberBehindSquare = splits[1];
            }

            if (numberBehindSquare.isEmpty()) {
                numberBehindSquare = subString;
            }

            String newReplace = "sqrt(" + numberBehindSquare + ")";
            String oldReplace = "√ " + numberBehindSquare;

            calculation = calculation.replace(oldReplace, newReplace);
        }
        return calculation;
    }

    private String calculateString(String calculation) {
        Expression expression = new ExpressionBuilder(calculation).build();
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

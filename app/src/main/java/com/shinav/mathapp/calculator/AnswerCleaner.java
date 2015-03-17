package com.shinav.mathapp.calculator;

import javax.inject.Inject;

public class AnswerCleaner {

    private static final int AMOUNT_OF_DECIMALS = 5;

    @Inject
    public AnswerCleaner() { }

    public String clean(String answer) {
        String roundOfAnswer = roundOnDecimals(answer, AMOUNT_OF_DECIMALS);
        String strippedAnswer = stripDotZero(roundOfAnswer);
        String cleanAnswer = replaceDotsWithCommas(strippedAnswer);

        return cleanAnswer;
    }

    public String roundOnDecimals(String answer, int amountOfDecimals) {

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

    public String stripDotZero(String answer) {
        if (answer.endsWith(".0")) {
            answer = answer.substring(0, answer.length() - 2);
        }
        return answer;
    }

    public String replaceDotsWithCommas(String answer) {
        return answer.replace(".", ",");
    }

}

package com.shinav.mathapp.calculator;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

public class EquationHandler {

    @Inject
    public EquationHandler() { }

    public String handleParenthesis(String equation) {
        int openCounter = StringUtils.countMatches(equation, "(");
        int closeCounter = StringUtils.countMatches(equation, ")");

        if (openCounter == closeCounter) {
            equation += "(";
        } else {
            if (!equation.endsWith("(")) {
                equation += ")";
            }
        }

        return equation;
    }

    public String handleBackspace(String equation) {
        int amountToRemove = equation.endsWith(" ") ? 3 : 1;
        return equation.substring(0, equation.length() - amountToRemove);
    }

    public String handleOperator(String equation, String operator) {
        equation += (" " + operator + " ");
        return equation;
    }

    public String handleNumber(String equation, String number) {
        equation += number;
        return equation;
    }

    public String handleResumedCalculating(String equation, String answer) {
        equation += answer;
        return equation;
    }

}

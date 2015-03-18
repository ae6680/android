package com.shinav.mathapp.calculator;

import org.apache.commons.lang3.StringUtils;

public class OperationHandler {
    public String handleParenthesis(String equation) {
        int openCounter = StringUtils.countMatches(equation, "(");
        int closeCounter = StringUtils.countMatches(equation, ")");

        if (openCounter == closeCounter) {
            return "(";
        } else {
            if (!equation.endsWith("(")) {
                return ")";
            }
        }

        return "";
    }

    public String handleBackspace(String equation) {
        int amountToRemove = equation.endsWith(" ") ? 3 : 1;
        return equation.substring(0, equation.length() - amountToRemove);
    }

}

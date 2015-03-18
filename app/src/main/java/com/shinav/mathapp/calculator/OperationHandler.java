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
}

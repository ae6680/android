package com.shinav.mathapp.calculator;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import javax.inject.Inject;

import timber.log.Timber;

public class Calculator {

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
            String filteredEquation = filterEquation(equation);

            String rawAnswer = calculateEquation(filteredEquation);

            answer = cleanAnswer(rawAnswer);

        } catch (IllegalArgumentException e) {
            Timber.e(e.getMessage());
        }

        return answer;
    }

    private String filterEquation(String equation) {
        return equationFilterer.filter(equation);
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

    private String cleanAnswer(String rawAnswer) {
        return answerCleaner.clean(rawAnswer);
    }

}

package com.shinav.mathapp.firebase;

import com.shinav.mathapp.question.Question;

import java.util.Map;

public class FirebaseParser {

    public static Question parseQuestion(Map<String, Object> newQuestion) {
        Question question = new Question();

        String answer = newQuestion.get(FirebaseInterface.Question.ANSWER).toString();
        String value = newQuestion.get(FirebaseInterface.Question.VALUE).toString();
        String title = newQuestion.get(FirebaseInterface.Question.TITLE).toString();
        boolean calculatorAllowed = (boolean) newQuestion.get(FirebaseInterface.Question.CALCULATOR_ALLOWED);

        question.setAnswer(answer);
        question.setValue(value);
        question.setTitle(title);
        question.setCalculatorAllowed(calculatorAllowed);

        return question;
    }
}

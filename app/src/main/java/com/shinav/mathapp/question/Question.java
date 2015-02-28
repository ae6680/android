package com.shinav.mathapp.question;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class Question extends RealmObject implements Serializable {

    private String value;
    private boolean calculatorAllowed;
    private QuestionHint hint;
    private String title;
    private String answer;

    public Question() { }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isCalculatorAllowed() {
        return calculatorAllowed;
    }

    public void setCalculatorAllowed(boolean calculatorAllowed) {
        this.calculatorAllowed = calculatorAllowed;
    }

    public QuestionHint getHint() {
        return hint;
    }

    public void setHint(QuestionHint hint) {
        this.hint = hint;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}

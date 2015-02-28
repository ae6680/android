package com.shinav.mathapp.question;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class QuestionHint extends RealmObject implements Serializable {

    private int questionId;
    private String title;
    private String value;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

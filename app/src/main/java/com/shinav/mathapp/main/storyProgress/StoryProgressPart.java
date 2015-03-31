package com.shinav.mathapp.main.storyProgress;

import com.shinav.mathapp.question.Question;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class StoryProgressPart extends RealmObject {

    private Question question;
    private String givenAnswer;

    public StoryProgressPart() { }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }
}

package com.shinav.mathapp.db.pojo;

public class Question {

    private String key;

    private String value;
    private String title;
    private String answer;
    private String explanation;

    private QuestionApproach questionApproach;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public void setQuestionApproach(QuestionApproach questionApproach) {
        this.questionApproach = questionApproach;
    }

    public QuestionApproach getQuestionApproach() {
        return questionApproach;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}

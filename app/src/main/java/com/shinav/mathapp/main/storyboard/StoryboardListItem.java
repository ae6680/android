package com.shinav.mathapp.main.storyboard;

public class StoryboardListItem {

    public static final int STATE_UNMADE = 0;
    public static final int STATE_PASS = 1;
    public static final int STATE_FAIL = 2;

    private final String questionKey;
    private final String questionTitle;
    private final String lastGivenAnswer;
    private final int state;

    public StoryboardListItem(
            String questionKey,
            String questionTitle,
            String lastGivenAnswer,
            int state)
    {
        this.questionKey = questionKey;
        this.questionTitle = questionTitle;
        this.lastGivenAnswer = lastGivenAnswer;
        this.state = state;
    }

    public String getQuestionKey() {
        return questionKey;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public String getLastGivenAnswer() {
        return lastGivenAnswer;
    }

    public int getState() {
        return state;
    }

}

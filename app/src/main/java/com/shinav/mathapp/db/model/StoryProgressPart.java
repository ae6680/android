package com.shinav.mathapp.db.model;

import android.content.ContentValues;

import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.GIVEN_ANSWER;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.STORY_PROGRESS_KEY;

public class StoryProgressPart {

    private String key;

    private String storyProgressKey;
    private String questionKey;
    private String givenAnswer;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStoryProgressKey() {
        return storyProgressKey;
    }

    public void setStoryProgressKey(String storyProgressKey) {
        this.storyProgressKey = storyProgressKey;
    }

    public String getQuestionKey() {
        return questionKey;
    }

    public void setQuestionKey(String questionKey) {
        this.questionKey = questionKey;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put(KEY, getKey());
        values.put(STORY_PROGRESS_KEY, getStoryProgressKey());
        values.put(QUESTION_KEY, getQuestionKey());
        values.put(GIVEN_ANSWER, getGivenAnswer());

        return values;
    }

}

package com.shinav.mathapp.db.model;

import android.database.Cursor;

import static com.shinav.mathapp.db.helper.Tables.StoryPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryPart.POSITION;
import static com.shinav.mathapp.db.helper.Tables.StoryPart.STORY_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryPart.TYPE;
import static com.shinav.mathapp.db.helper.Tables.StoryPart.TYPE_KEY;

public class StoryPart {

    private String key;
    private String storyKey;

    private int position;
    private String type;
    private String typeKey;

    public StoryPart() {  }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStoryKey() {
        return storyKey;
    }

    public void setStoryKey(String storyKey) {
        this.storyKey = storyKey;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    public boolean isApproach() {
        return getType().equals("approach");
    }

    public boolean isApproachFeedback() {
        return getType().equals("approachFeedback");
    }

    public boolean isConversation() {
        return getType().equals("conversation");
    }

    public boolean isQuestion() {
        return getType().equals("question");
    }

    public static StoryPart fromCursor(Cursor c) {
        StoryPart storyPart = new StoryPart();

        storyPart.setKey(c.getString(c.getColumnIndex(KEY)));
        storyPart.setStoryKey(c.getString(c.getColumnIndex(STORY_KEY)));
        storyPart.setPosition(c.getInt(c.getColumnIndex(POSITION)));
        storyPart.setType(c.getString(c.getColumnIndex(TYPE)));
        storyPart.setTypeKey(c.getString(c.getColumnIndex(TYPE_KEY)));

        return storyPart;
    }

}

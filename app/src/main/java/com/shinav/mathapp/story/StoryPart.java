package com.shinav.mathapp.story;

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

        storyPart.setKey(getString(c, KEY));
        storyPart.setStoryKey(getString(c, STORY_KEY));
        storyPart.setPosition(getInt(c, POSITION));
        storyPart.setType(getString(c, TYPE));
        storyPart.setTypeKey(getString(c, TYPE_KEY));

        return storyPart;
    }

    private static String getString(Cursor c, String column) {
        return c.getString(c.getColumnIndex(column));
    }

    private static int getInt(Cursor c, String column) {
        return c.getInt(c.getColumnIndex(column));
    }
}

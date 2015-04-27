package com.shinav.mathapp.main.storyboard;

public interface StoryboardFrameListItem {

    public static final int STATE_CLOSED = 0;
    public static final int STATE_OPENED = 1;
    public static final int STATE_PASSED = 2;
    public static final int STATE_FAILED = 3;

    public static final String TYPE_CUTSCENE = "type_cutscene";
    public static final String TYPE_QUESTION = "type_question";

    public String getKey();
    public String getType();
    public String getTitle();
    public int getState();
    public String getBackgroundImage();

}

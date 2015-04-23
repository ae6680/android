package com.shinav.mathapp.main.storyboard;

public interface StoryboardFrameListItem {

    public static final int STATE_CLOSED = -1;
    public static final int STATE_OPEN = 0;
    public static final int STATE_PASS = 1;
    public static final int STATE_FAIL = 2;

    public String getKey();
    public String getTitle();
    public int getState();
    public String getBackgroundImage();

}

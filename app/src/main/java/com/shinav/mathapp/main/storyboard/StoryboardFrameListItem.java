package com.shinav.mathapp.main.storyboard;

public interface StoryboardFrameListItem {

    public static final int STATE_CLOSED = 0;
    public static final int STATE_OPENED = 1;
    public static final int STATE_PASSED = 2;
    public static final int STATE_FAILED = 3;

    public String getKey();
    public String getTitle();
    public int getState();
    public String getBackgroundImage();

}

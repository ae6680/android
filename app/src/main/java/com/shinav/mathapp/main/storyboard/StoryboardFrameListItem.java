package com.shinav.mathapp.main.storyboard;

public interface StoryboardFrameListItem {

    public static final int STATE_CLOSED = -1;
    public static final int STATE_OPENED = 0;
    public static final int STATE_PASSED = 1;
    public static final int STATE_FAILED = 2;

    public String getKey();
    public String getTitle();
    public int getState();
    public String getBackgroundImage();

}

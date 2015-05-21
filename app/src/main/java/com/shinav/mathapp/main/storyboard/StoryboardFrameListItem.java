package com.shinav.mathapp.main.storyboard;

public interface StoryboardFrameListItem {

    int STATE_CLOSED = 0;
    int STATE_OPENED = 1;
    int STATE_PASSED = 2;
    int STATE_FAILED = 3;

    String TYPE_CUTSCENE = "type_cutscene";
    String TYPE_QUESTION = "type_question";

    String getKey();
    String getType();
    String getTitle();
    int getState();
    void setState(int state);
    String getBackgroundImage();

}

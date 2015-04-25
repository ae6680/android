package com.shinav.mathapp.event;

public class StoryboardFrameListItemClickedEvent {

    private final String frameTypeKey;

    public StoryboardFrameListItemClickedEvent(String frameTypeKey) {
        this.frameTypeKey = frameTypeKey;
    }

    public String getFrameTypeKey() {
        return frameTypeKey;
    }

}

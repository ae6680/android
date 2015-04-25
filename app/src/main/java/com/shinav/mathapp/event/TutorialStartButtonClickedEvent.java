package com.shinav.mathapp.event;

public class TutorialStartButtonClickedEvent {

    private final int resourceId;

    public TutorialStartButtonClickedEvent(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }
    
}

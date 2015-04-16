package com.shinav.mathapp.event;

public class TutorialStartButtonClicked {

    private final int resourceId;

    public TutorialStartButtonClicked(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }
    
}

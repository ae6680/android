package com.shinav.mathapp.event;

public class TutorialStartButtonClicked {
    private final String perspective;

    public TutorialStartButtonClicked(String perspective) {
        this.perspective = perspective;
    }

    public String getPerspective() {
        return perspective;
    }

}

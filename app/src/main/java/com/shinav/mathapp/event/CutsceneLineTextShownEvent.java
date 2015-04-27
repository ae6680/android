package com.shinav.mathapp.event;

public class CutsceneLineTextShownEvent {
    private final int position;

    public CutsceneLineTextShownEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

}

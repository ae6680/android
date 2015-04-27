package com.shinav.mathapp.main.storyboard.viewHolder;

import android.view.View;

import com.shinav.mathapp.main.storyboard.viewHolder.stateButton.CutsceneStateImageButton;

import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.STATE_CLOSED;
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.STATE_OPENED;

public class CutsceneViewHolder extends StoryboardFrameViewHolder {

    public CutsceneViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setState(int cutsceneState) {
        super.setState(cutsceneState);
        setStateUI(cutsceneState);
    }

    private void setStateUI(int cutsceneState) {
        CutsceneStateImageButton cutsceneStateImageButton =
                (CutsceneStateImageButton) state;

        switch (cutsceneState) {
            case STATE_CLOSED:
                cutsceneStateImageButton.setClosed(true);
                showBackgroundDarkened();
                break;
            case STATE_OPENED:
                cutsceneStateImageButton.setOpened(true);
                showBackgroundLightened();
                break;
        }
    }

}

package com.shinav.mathapp.main.storyboard.viewHolder;

import android.view.View;

import com.shinav.mathapp.main.storyboard.viewHolder.stateButton.QuestionStateImageButton;

import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.STATE_CLOSED;
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.STATE_FAILED;
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.STATE_OPENED;
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.STATE_PASSED;

public class QuestionViewHolder extends StoryboardFrameViewHolder {

    public QuestionViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setState(int questionState) {
        super.setState(questionState);
        setStateUI(questionState);
    }

    private void setStateUI(int questionState) {
        QuestionStateImageButton questionStateImageButton =
                (QuestionStateImageButton) state;

        switch (questionState) {
            case STATE_CLOSED:
                questionStateImageButton.setClosed(true);
                showBackgroundDarkened();
                hideTitle();
                break;
            case STATE_OPENED:
                questionStateImageButton.setOpened(true);
                showBackgroundLightened();
                showTitle();
                break;
            case STATE_PASSED:
                questionStateImageButton.setPassed(true);
                showBackgroundLightened();
                showTitle();
                break;
            case STATE_FAILED:
                questionStateImageButton.setFailed(true);
                showBackgroundLightened();
                showTitle();
                break;
        }
    }

}

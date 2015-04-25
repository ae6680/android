package com.shinav.mathapp.main.storyboard.viewHolder;

import android.view.View;

import com.shinav.mathapp.main.storyboard.viewHolder.stateButton.ConversationStateImageButton;

import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.STATE_CLOSED;
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.STATE_OPENED;

public class ConversationViewHolder extends StoryboardFrameViewHolder {

    public ConversationViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setState(int conversationState) {
        super.setState(conversationState);
        setStateUI(conversationState);
    }

    private void setStateUI(int conversationState) {
        ConversationStateImageButton conversationStateImageButton =
                (ConversationStateImageButton) state;

        switch (conversationState) {
            case STATE_CLOSED:
                conversationStateImageButton.setClosed(true);
                showBackgroundDarkened();
                break;
            case STATE_OPENED:
                conversationStateImageButton.setOpened(true);
                showBackgroundLightened();
                break;
        }
    }

}

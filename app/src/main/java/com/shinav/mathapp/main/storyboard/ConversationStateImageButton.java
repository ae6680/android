package com.shinav.mathapp.main.storyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.shinav.mathapp.R;

public class ConversationStateImageButton extends ImageButton {

    public static final int[] STATE_CLOSED = { R.attr.state_closed };
    public static final int[] STATE_OPENED = { R.attr.state_opened };

    private boolean isClosed = false;
    private boolean isOpened = false;

    public ConversationStateImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(false);
    }

    @Override public int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 4);

        if (isClosed) {
            mergeDrawableStates(drawableState, STATE_CLOSED);
        }

        if (isOpened) {
            mergeDrawableStates(drawableState, STATE_OPENED);
        }

        return drawableState;
    }

    public void setClosed(boolean isClosed) {
        reset();
        this.isClosed = isClosed;
    }

    public void setOpened(boolean isOpened) {
        reset();
        this.isOpened = isOpened;
    }

    private void reset() {
        isClosed = false;
        isOpened = false;
    }

}

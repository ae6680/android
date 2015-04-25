package com.shinav.mathapp.main.storyboard.viewHolder.stateButton;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.shinav.mathapp.R;

public class QuestionStateImageButton extends ImageButton {

    public static final int[] STATE_CLOSED = { R.attr.state_closed };
    public static final int[] STATE_OPENED = { R.attr.state_opened };
    public static final int[] STATE_PASSED = { R.attr.state_passed };
    public static final int[] STATE_FAILED = { R.attr.state_failed };

    private boolean isClosed = false;
    private boolean isOpened = false;
    private boolean isPassed = false;
    private boolean isFailed = false;

    public QuestionStateImageButton(Context context, AttributeSet attrs) {
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

        if (isPassed) {
            mergeDrawableStates(drawableState, STATE_PASSED);
        }

        if (isFailed) {
            mergeDrawableStates(drawableState, STATE_FAILED);
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

    public void setPassed(boolean isPassed) {
        reset();
        this.isPassed = isPassed;
    }

    public void setFailed(boolean isFailed) {
        reset();
        this.isFailed = isFailed;
    }

    private void reset() {
        isClosed = false;
        isOpened = false;
        isPassed = false;
        isFailed = false;
    }

}

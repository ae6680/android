package com.shinav.mathapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ViewFlipper;

public class CustomViewFlipper extends ViewFlipper {

    public CustomViewFlipper(Context context) {
        super(context);
    }

    public CustomViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFront(FlipCard view) {
        view.setViewFlipper(this);
        refresh(view, 0);
    }

    public void setBack(FlipCard view) {
        view.setViewFlipper(this);
        refresh(view, 1);
    }

    private void refresh(View view, int index) {
        removeView(getChildAt(index));
        addView(view, index);
    }

}

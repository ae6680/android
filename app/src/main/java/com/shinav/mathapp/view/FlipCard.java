package com.shinav.mathapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.animation.AnimationFactory;

public class FlipCard extends LinearLayout {

    public static final float PERCENTAGE_WIDTH_OF_SCREEN = 0.8F;
    public static final float PERCENTAGE_HEIGHT_OF_SCREEN = 0.55F;
    private ViewFlipper flipper;

    public FlipCard(Context context) {
        super(context);
    }

    public FlipCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlipCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setParams(View view) {
        view.setLayoutParams(new ViewGroup.LayoutParams(
                calculateWidth(),
                calculateHeight()
        ));
    }

    private int calculateWidth() {
        return (int) Math.floor(MyApplication.screenWidth * PERCENTAGE_WIDTH_OF_SCREEN);
    }

    private int calculateHeight() {
        return (int) Math.floor(MyApplication.screenHeight * PERCENTAGE_HEIGHT_OF_SCREEN);
    }

    public void setViewFlipper(ViewFlipper viewFlipper) {
        flipper = viewFlipper;
    }

    public void flip(AnimationFactory.FlipDirection flipDirection, int duration) {
        if (flipper != null)  {
            AnimationFactory.flipTransition(flipper, flipDirection, duration);
        } else {
            throw new NullPointerException("ViewFlipper must be set, call setViewFlipper()");
        }
    }

}

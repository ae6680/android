package com.shinav.mathapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.shinav.mathapp.MyApplication;

public class Card extends LinearLayout {

    public static final float PERCENTAGE_WIDTH_OF_SCREEN = 0.8F;
    public static final float PERCENTAGE_HEIGHT_OF_SCREEN = 0.55F;

    public Card(Context context) {
        super(context);
    }

    public Card(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Card(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int calculateWidth() {
        return (int) Math.floor(MyApplication.screenWidth * PERCENTAGE_WIDTH_OF_SCREEN);
    }

    private int calculateHeight() {
        return (int) Math.floor(MyApplication.screenHeight * PERCENTAGE_HEIGHT_OF_SCREEN);
    }

    public int getDefaultCardHeight() {
        return calculateHeight();
    }

    public void setParams(View view) {
        view.setLayoutParams(new ViewGroup.LayoutParams(
                calculateWidth(),
                calculateHeight()
        ));
    }

}

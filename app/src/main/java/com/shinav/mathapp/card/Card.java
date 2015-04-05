package com.shinav.mathapp.card;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.R;
import com.shinav.mathapp.view.ButterKnifeLayout;

public class Card extends ButterKnifeLayout {

    public static final float PERCENTAGE_HEIGHT_OF_SCREEN = 0.45F;

    public Card(Context context) {
        super(context);
    }

    public Card(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Card(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int calculateHeight() {
        return (int) Math.floor(MyApplication.screenHeight * PERCENTAGE_HEIGHT_OF_SCREEN);
    }

    public int getDefaultCardHeight() {
        return calculateHeight();
    }

    public void setLayoutParamsForViewPager(View view) {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                getDefaultCardHeight()
        );

        params.leftMargin = getResources().getDimensionPixelSize(R.dimen.card_pager_margin);
        params.rightMargin = getResources().getDimensionPixelSize(R.dimen.card_pager_margin);

        view.setLayoutParams(params);
    }


}

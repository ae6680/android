package com.shinav.mathapp.view;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.shinav.mathapp.R;

public class ViewPagerIndicator extends ImageButton {

    public ViewPagerIndicator(Context context) {
        super(context);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.view_pager_indicator);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.indicator_dimension),
                getResources().getDimensionPixelSize(R.dimen.indicator_dimension)
        );
        params.setMargins(10, 0, 10, 0);

        setLayoutParams(params);
    }

}

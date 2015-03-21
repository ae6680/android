package com.shinav.mathapp.view;

import android.content.Context;
import android.view.ViewGroup;
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
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(5, 0, 5, 0);

        setLayoutParams(params);
    }

}

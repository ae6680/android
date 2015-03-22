package com.shinav.mathapp.animation;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

public class HeightAnimation {

    public static ValueAnimator create(final View view, int newValue, boolean relative) {

        if (relative) {
           newValue += view.getMeasuredHeight();
        }

        ValueAnimator anim = ValueAnimator.ofInt(view.getMeasuredHeight(), newValue);

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = (int) animation.getAnimatedValue();
                view.setLayoutParams(layoutParams);
            }
        });

        return anim;
    }

}

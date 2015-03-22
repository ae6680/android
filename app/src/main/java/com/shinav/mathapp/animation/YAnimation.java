package com.shinav.mathapp.animation;

import android.animation.ObjectAnimator;
import android.view.View;

public class YAnimation {

    public static ObjectAnimator create(View view, float relativeEndValue) {
        return ObjectAnimator.ofFloat(view, "Y", view.getY(), view.getY() + relativeEndValue);
    }

}

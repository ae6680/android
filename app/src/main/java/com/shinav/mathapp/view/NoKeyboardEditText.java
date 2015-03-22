package com.shinav.mathapp.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class NoKeyboardEditText extends EditText {

    public NoKeyboardEditText(Context context) {
        super(context);
    }

    public NoKeyboardEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoKeyboardEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override public boolean onTouchEvent(@NonNull MotionEvent event) {
        super.onTouchEvent(event);

        InputMethodManager imm = (InputMethodManager) this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindowToken(), 0);

        return true;
    }

}

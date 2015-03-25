package com.shinav.mathapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;

public abstract class ButterKnifeLayout extends RelativeLayout {

    public ButterKnifeLayout(Context context) {
        super(context);
    }

    public ButterKnifeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButterKnifeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public View inflate(int layoutResId, ViewGroup root, boolean attachToRoot) {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View view = inflater.inflate(layoutResId, root, attachToRoot);

        ButterKnife.inject(this, view);

        return view;
    }

}

package com.shinav.mathapp.questionApproachFeedback;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.view.ButterKnifeLayout;

import butterknife.InjectView;

public class QAFViewPagerIndicator extends ButterKnifeLayout {

    @InjectView(R.id.indicator_background) RelativeLayout background;
    @InjectView(R.id.indicator_text) TextView text;

    public QAFViewPagerIndicator(Context context) {
        super(context);
        init();
    }

    public QAFViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QAFViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(R.layout.question_approach_feedback_view_pager_indicator, this, true);
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setFail() {
        background.setBackgroundResource(R.drawable.fail_view_pager_indicator);
    }

    public void setPass() {
        background.setBackgroundResource(R.drawable.pass_view_pager_indicator);
    }

}

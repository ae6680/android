package com.shinav.mathapp.questionApproach.feedback;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.shinav.mathapp.db.pojo.QuestionApproachPart;
import com.shinav.mathapp.injection.component.Injector;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class QAFViewPager extends ViewPager {

    @Inject QAFViewPagerAdapter adapter;

    private LinearLayout indicatorContainer;

    List<QAFViewPagerPage> pages = new ArrayList<>();

    public QAFViewPager(Context context) {
        super(context);
        init();
    }

    public QAFViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Injector.getViewComponent(this.getContext()).inject(this);
        setAdapter(adapter);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        for(int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if(h > height) height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setupQuestionApproachParts(
            List<QuestionApproachPart> givenList,
            List<QuestionApproachPart> correctList
    ) {

        for (int i = 0; i < givenList.size(); i++) {
            QuestionApproachPart givenPart = givenList.get(i);
            QuestionApproachPart correctPart = correctList.get(i);

            addIndicator(i, givenPart);
            addPage(givenPart, correctPart);
        }

        adapter.setPages(pages);
    }

    private void addPage(QuestionApproachPart givenPart, QuestionApproachPart correctPart) {
        QAFViewPagerPage page = new QAFViewPagerPage(this.getContext());
        page.setParts(givenPart, correctPart);

        pages.add(page);
    }

    private void addIndicator(final int i, QuestionApproachPart givenPart) {
        QAFViewPagerIndicator indicator = new QAFViewPagerIndicator(this.getContext());

        if (givenPart.getPosition() == i) {
            indicator.setPass();
        }

        indicator.setText(String.valueOf(i+1));

        indicator.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                setCurrentItem(i);
            }
        });

        indicatorContainer.addView(indicator);
    }

    public void setIndicatorContainer(LinearLayout indicatorContainer) {
        this.indicatorContainer = indicatorContainer;
    }

    public LinearLayout getIndicatorContainer() {
        return indicatorContainer;
    }
}

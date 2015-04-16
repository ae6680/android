package com.shinav.mathapp.questionApproach.feedback;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.shinav.mathapp.db.pojo.QuestionApproachPart;
import com.shinav.mathapp.injection.component.ComponentFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class QAFViewPager extends ViewPager {

    @Inject QAFViewPagerAdapter adapter;

    public QAFViewPager(Context context) {
        super(context);
        init();
    }

    public QAFViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        ComponentFactory.getViewComponent(this.getContext()).inject(this);
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
        List<QAFViewPagerPage> pages = new ArrayList<>();

        for (int i = 0; i < givenList.size(); i++) {
            QAFViewPagerPage page = createPage(givenList.get(i), correctList.get(i));

            pages.add(page);
        }

        adapter.setPages(pages);
    }

    private QAFViewPagerPage createPage(QuestionApproachPart givenPart, QuestionApproachPart correctPart) {
        QAFViewPagerPage page = new QAFViewPagerPage(this.getContext());
        page.setParts(givenPart, correctPart);

        return page;
    }

}

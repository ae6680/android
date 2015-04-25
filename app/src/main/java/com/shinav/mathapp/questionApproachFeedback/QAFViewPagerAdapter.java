package com.shinav.mathapp.questionApproachFeedback;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class QAFViewPagerAdapter extends PagerAdapter {

    private List<QAFViewPagerPage> pages = Collections.emptyList();

    @Inject
    public QAFViewPagerAdapter() { }

    @Override public Object instantiateItem(ViewGroup container, int position) {
        View view = pages.get(position);
        container.addView(view);

        return view;
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override public int getCount() {
        return pages.size();
    }

    @Override public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setPages(List<QAFViewPagerPage> pages) {
        this.pages = pages;
        notifyDataSetChanged();
    }

}

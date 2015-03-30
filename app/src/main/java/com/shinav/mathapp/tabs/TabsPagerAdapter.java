package com.shinav.mathapp.tabs;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TabsPagerAdapter extends PagerAdapter {

    private List<ViewGroup> tabs = new ArrayList<>();

    @Inject
    public TabsPagerAdapter() { }

    @Override public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup viewGroup = tabs.get(position);
        container.addView(viewGroup);

        return viewGroup;
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeViewAt(position);
    }

    @Override public int getCount() {
        return tabs.size();
    }

    @Override public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void addTab(ViewGroup viewGroup) {
        tabs.add(viewGroup);
        notifyDataSetChanged();
    }

}

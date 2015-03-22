package com.shinav.mathapp.view;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

public class CardsAdapter extends PagerAdapter {

    private List<Card> cards = Collections.emptyList();

    @Override public Object instantiateItem(ViewGroup container, int position) {
        View view = cards.get(position);
        container.addView(view);

        return view;
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override public int getCount() {
        return cards.size();
    }

    @Override public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }
}

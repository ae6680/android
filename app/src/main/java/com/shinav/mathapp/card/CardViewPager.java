package com.shinav.mathapp.card;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.shinav.mathapp.R;
import com.shinav.mathapp.view.ViewPagerIndicator;

import java.util.List;

public class CardViewPager extends ViewPager {

    private CardsAdapter adapter;
    private LinearLayout indicatorContainer;
    private List<Card> cards;

    public CardViewPager(Context context) {
        super(context);
        init();
    }

    public CardViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        adapter = new CardsAdapter();
        this.setAdapter(adapter);

        int gutter = getResources().getDimensionPixelSize(R.dimen.card_pager_gutter);
        int margin = getResources().getDimensionPixelSize(R.dimen.card_pager_margin);
        this.setPageMargin(-(gutter + margin));

        this.setOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override public void onPageSelected(int position) {
                handleIndicatorSelection(position);
            }
        });
    }

    @Override public void setCurrentItem(int item) {
        super.setCurrentItem(item);
        handleIndicatorSelection(item);
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
        adapter.setCards(cards);

        generateIndicator();
    }

    public void setIndicator(LinearLayout viewPagerIndicator) {
        this.indicatorContainer = viewPagerIndicator;
        generateIndicator();
    }

    public void addCards(List<Card> cards) {
        this.cards.addAll(cards);
        adapter.setCards(this.cards);
        generateIndicator();
    }

    private void generateIndicator() {
        if (indicatorContainer != null) {
            indicatorContainer.removeAllViews();

            for (int i = 0; i < adapter.getCount(); i++) {
                addIndicatorDot(i);
            }
        }
    }

    private void addIndicatorDot(int i) {
        ViewPagerIndicator indicator = new ViewPagerIndicator(this.getContext());
        indicator.setTag("indicator-"+i);

        final int position = i;
        indicator.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                CardViewPager.this.setCurrentItem(position);
            }
        });

        indicatorContainer.addView(indicator);
    }

    private void handleIndicatorSelection(int position) {
        for (int i = 0; i < indicatorContainer.getChildCount(); i++) {
            ViewPagerIndicator indicator =
                    (ViewPagerIndicator) indicatorContainer.findViewWithTag("indicator-"+i);

            indicator.setSelected(position == i);
        }
    }

}

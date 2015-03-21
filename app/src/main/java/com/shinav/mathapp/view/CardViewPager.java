package com.shinav.mathapp.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.shinav.mathapp.R;
import com.shinav.mathapp.question.CardsAdapter;

import java.util.List;

public class CardViewPager extends ViewPager {

    private CardsAdapter adapter;

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

        this.setCurrentItem(1);
    }

    public void setCards(List<Card> cards) {
        adapter.setCards(cards);
    }

}

package com.shinav.mathapp.chapter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.shinav.mathapp.R;
import com.shinav.mathapp.bus.BusProvider;
import com.shinav.mathapp.event.OnNextQuestionClickedEvent;
import com.shinav.mathapp.question.QuestionViewPagerAdapter;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChapterActivity extends FragmentActivity {

    @InjectView(R.id.view_pager) DisableableViewPager viewPager;
    private QuestionViewPagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        ButterKnife.inject(this);

        initViewPager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        BusProvider.getUIBusInstance().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        BusProvider.getUIBusInstance().unregister(this);
    }

    private void initViewPager() {
        adapter = new QuestionViewPagerAdapter(getSupportFragmentManager());
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(adapter);
    }

    @Subscribe
    public void onNextQuestionClicked(OnNextQuestionClickedEvent event) {
        int nextPosition = viewPager.getCurrentItem() + 1;
        if (nextPosition < adapter.getCount()) {
            viewPager.setCurrentItem(nextPosition);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_from_outside, R.anim.slide_right_to_outside);
    }

}

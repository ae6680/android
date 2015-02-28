package com.shinav.mathapp.story;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.shinav.mathapp.R;
import com.shinav.mathapp.bus.BusProvider;
import com.shinav.mathapp.event.OnNextQuestionClickedEvent;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class StoryActivity extends FragmentActivity {

    @InjectView(R.id.view_pager) DisableableViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

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
        StoryViewPagerAdapter adapter = new StoryViewPagerAdapter(getSupportFragmentManager());
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(adapter);
    }

    @Subscribe
    public void onNextQuestionClicked(OnNextQuestionClickedEvent event) {
        int nextPosition = viewPager.getCurrentItem() + 1;
        if (nextPosition < viewPager.getChildCount()) {
            viewPager.setCurrentItem(nextPosition);
        }
    }

}

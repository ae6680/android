package com.shinav.mathapp.story;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.shinav.mathapp.R;
import com.shinav.mathapp.bus.BusProvider;
import com.shinav.mathapp.event.OnNextQuestionClickedEvent;
import com.shinav.mathapp.question.Question;
import com.shinav.mathapp.question.QuestionFragment;
import com.shinav.mathapp.repository.RealmRepository;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class StoryActivity extends FragmentActivity {

    @InjectView(R.id.view_pager) DisableableViewPager viewPager;
    private ViewPagerAdapter adapter;
    private List<Fragment> fragmentPages = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        ButterKnife.inject(this);

        initFragmentPages();
        initViewPager();
    }

    private void initFragmentPages() {
        List<Question> questions = RealmRepository.getInstance().getQuestions();
        for (Question question : questions) {
            fragmentPages.add(QuestionFragment.newInstance(question));
        }
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
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragmentPages.get(position);
        }

        @Override
        public int getCount() {
            return fragmentPages.size();
        }
    }

    @Subscribe
    public void onNextQuestionClicked(OnNextQuestionClickedEvent event) {
        int nextPosition = viewPager.getCurrentItem() + 1;
        if (nextPosition < viewPager.getChildCount()) {
            viewPager.setCurrentItem(nextPosition);
        }
    }

}

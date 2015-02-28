package com.shinav.mathapp.story;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.shinav.mathapp.question.Question;
import com.shinav.mathapp.question.QuestionFragment;
import com.shinav.mathapp.repository.RealmRepository;

import java.util.ArrayList;
import java.util.List;

public class StoryViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<QuestionFragment> fragmentPages = new ArrayList<>();

    public StoryViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fetchQuestions();
    }

    private void fetchQuestions() {
        List<Question> questions = RealmRepository.getInstance().getQuestions();
        for (Question question : questions) {
            fragmentPages.add(QuestionFragment.newInstance(question));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentPages.get(position);
    }

    @Override
    public int getCount() {
        return fragmentPages.size();
    }

}

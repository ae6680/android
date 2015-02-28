package com.shinav.mathapp.story;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.firebase.FirebaseInterface;
import com.shinav.mathapp.firebase.FirebaseParser;
import com.shinav.mathapp.firebase.FirebaseProvider;
import com.shinav.mathapp.question.Question;
import com.shinav.mathapp.question.QuestionFragment;

import java.util.ArrayList;
import java.util.Map;

public class StoryViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<QuestionFragment> fragmentPages = new ArrayList<>();

    public StoryViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fetchQuestions();
    }

    private void fetchQuestions() {

        FirebaseProvider.getBaseRef().child(FirebaseInterface.Nodes.QUESTIONS)
                .addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Map<String, Object> newQuestion = (Map<String, Object>) dataSnapshot.getValue();

                        Question question = FirebaseParser.parseQuestion(newQuestion);

                        fragmentPages.add(QuestionFragment.newInstance(question));

                        notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {  }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {  }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {  }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {  }

                });

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

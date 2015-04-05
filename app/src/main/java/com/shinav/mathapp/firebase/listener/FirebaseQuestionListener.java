package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.mapper.QuestionMapper;
import com.shinav.mathapp.db.pojo.Question;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

public class FirebaseQuestionListener implements ChildEventListener {

    private final FirebaseParser firebaseParser;
    private final QuestionMapper questionMapper;

    @Inject
    public FirebaseQuestionListener(FirebaseParser firebaseParser, QuestionMapper questionMapper) {
        this.firebaseParser = firebaseParser;
        this.questionMapper = questionMapper;
    }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Question question = firebaseParser.parseQuestion(dataSnapshot);
        questionMapper.insert(question);
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override public void onCancelled(FirebaseError firebaseError) {

    }

}

package com.shinav.mathapp.firebase.listener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.shinav.mathapp.db.dataMapper.QuestionApproachPartMapper;
import com.shinav.mathapp.db.pojo.QuestionApproachPart;
import com.shinav.mathapp.firebase.FirebaseParser;

import javax.inject.Inject;

import timber.log.Timber;

public class FirebaseQuestionApproachPartListener implements ChildEventListener {

    private final FirebaseParser firebaseParser;
    private final QuestionApproachPartMapper questionApproachPartMapper;

    @Inject
    public FirebaseQuestionApproachPartListener(FirebaseParser firebaseParser, QuestionApproachPartMapper questionApproachPartMapper) {
        this.firebaseParser = firebaseParser;
        this.questionApproachPartMapper = questionApproachPartMapper;
    }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        QuestionApproachPart questionApproachPart = firebaseParser.parseApproachPart(dataSnapshot);
        questionApproachPartMapper.insert(questionApproachPart);

        Timber.d("Firebase added a QuestionApproachPart");
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        QuestionApproachPart questionApproachPart = firebaseParser.parseApproachPart(dataSnapshot);
        questionApproachPartMapper.update(questionApproachPart);

        Timber.d("Firebase changed a QuestionApproachPart");
    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        questionApproachPartMapper.delete(dataSnapshot.getKey());

        Timber.d("Firebase removed a QuestionApproachPart");
    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {  }

    @Override public void onCancelled(FirebaseError firebaseError) {  }

}

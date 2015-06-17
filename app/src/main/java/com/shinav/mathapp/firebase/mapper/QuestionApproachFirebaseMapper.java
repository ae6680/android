package com.shinav.mathapp.firebase.mapper;

import com.firebase.client.DataSnapshot;
import com.shinav.mathapp.db.pojo.QuestionApproach;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.QuestionApproach.QUESTION_KEY;

public class QuestionApproachFirebaseMapper extends FirebaseMapper<QuestionApproach> {

    @Inject public QuestionApproachFirebaseMapper() { }

    @Override public QuestionApproach fromDataSnapshot(DataSnapshot dataSnapshot) {
        QuestionApproach questionApproach = new QuestionApproach();

        String questionKey = getString(dataSnapshot, QUESTION_KEY);

        questionApproach.setKey(dataSnapshot.getKey());
        questionApproach.setQuestionKey(questionKey);

        return questionApproach;
    }

}

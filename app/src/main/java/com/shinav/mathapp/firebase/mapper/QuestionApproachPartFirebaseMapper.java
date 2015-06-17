package com.shinav.mathapp.firebase.mapper;

import com.firebase.client.DataSnapshot;
import com.shinav.mathapp.db.pojo.QuestionApproachPart;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.POSITION;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.QUESTION_APPROACH_KEY;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart.VALUE;

public class QuestionApproachPartFirebaseMapper extends FirebaseMapper<QuestionApproachPart> {

    @Inject public QuestionApproachPartFirebaseMapper() { }

    @Override public QuestionApproachPart fromDataSnapshot(DataSnapshot dataSnapshot) {
        QuestionApproachPart questionApproachPart = new QuestionApproachPart();

        String approachKey = getString(dataSnapshot, QUESTION_APPROACH_KEY);
        String position =    getString(dataSnapshot, POSITION);
        String value =       getString(dataSnapshot, VALUE);

        questionApproachPart.setKey(dataSnapshot.getKey());
        questionApproachPart.setApproachKey(approachKey);
        questionApproachPart.setPosition(Integer.parseInt(position));
        questionApproachPart.setValue(value);

        return questionApproachPart;
    }

}

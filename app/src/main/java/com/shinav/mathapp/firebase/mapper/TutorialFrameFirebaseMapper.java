package com.shinav.mathapp.firebase.mapper;

import com.firebase.client.DataSnapshot;
import com.shinav.mathapp.db.pojo.TutorialFrame;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.FRAME_TYPE;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.FRAME_TYPE_KEY;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.POSITION;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.TUTORIAL_KEY;

public class TutorialFrameFirebaseMapper extends FirebaseMapper<TutorialFrame> {

    @Inject public TutorialFrameFirebaseMapper() { }

    @Override public TutorialFrame fromDataSnapshot(DataSnapshot dataSnapshot) {
        TutorialFrame tutorialFrame = new TutorialFrame();

        String tutorialKey =    getString(dataSnapshot, TUTORIAL_KEY);
        String position =       getString(dataSnapshot, POSITION);
        String frameType =      getString(dataSnapshot, FRAME_TYPE);
        String frameTypeKey =   getString(dataSnapshot, FRAME_TYPE_KEY);

        tutorialFrame.setKey(dataSnapshot.getKey());
        tutorialFrame.setTutorialKey(tutorialKey);
        tutorialFrame.setPosition(Integer.parseInt(position));
        tutorialFrame.setFrameType(frameType);
        tutorialFrame.setFrameTypeKey(frameTypeKey);

        return tutorialFrame;
    }

}

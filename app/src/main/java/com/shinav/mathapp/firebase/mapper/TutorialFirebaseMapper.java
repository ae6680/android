package com.shinav.mathapp.firebase.mapper;

import com.firebase.client.DataSnapshot;
import com.shinav.mathapp.db.pojo.Tutorial;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Tutorial.TITLE;

public class TutorialFirebaseMapper extends FirebaseMapper<Tutorial> {

    @Inject public TutorialFirebaseMapper() { }

    @Override public Tutorial fromDataSnapshot(DataSnapshot dataSnapshot) {
        Tutorial tutorial = new Tutorial();

        String title = getString(dataSnapshot, TITLE);

        tutorial.setKey(dataSnapshot.getKey());
        tutorial.setTitle(title);

        return tutorial;
    }

}

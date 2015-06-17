package com.shinav.mathapp.firebase.mapper;

import com.firebase.client.DataSnapshot;
import com.shinav.mathapp.db.pojo.Storyboard;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Storyboard.TITLE;

public class StoryboardFirebaseMapper extends FirebaseMapper<Storyboard> {

    @Inject public StoryboardFirebaseMapper() { }

    @Override public Storyboard fromDataSnapshot(DataSnapshot dataSnapshot) {
        Storyboard storyboard = new Storyboard();

        String title = getString(dataSnapshot, TITLE);

        storyboard.setKey(dataSnapshot.getKey());
        storyboard.setTitle(title);

        return storyboard;
    }

}

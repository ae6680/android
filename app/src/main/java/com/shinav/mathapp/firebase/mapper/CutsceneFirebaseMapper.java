package com.shinav.mathapp.firebase.mapper;

import com.firebase.client.DataSnapshot;
import com.shinav.mathapp.db.pojo.Cutscene;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Cutscene.BACKGROUND_IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.Cutscene.TITLE;

public class CutsceneFirebaseMapper extends FirebaseMapper<Cutscene> {

    @Inject public CutsceneFirebaseMapper() { }

    @Override public Cutscene fromDataSnapshot(DataSnapshot dataSnapshot) {
        Cutscene cutscene = new Cutscene();

        String title =      getString(dataSnapshot, TITLE);
        String imageUrl =   getString(dataSnapshot, BACKGROUND_IMAGE_URL);

        cutscene.setKey(dataSnapshot.getKey());
        cutscene.setTitle(title);
        cutscene.setBackgroundImageUrl(imageUrl);

        return cutscene;
    }

}

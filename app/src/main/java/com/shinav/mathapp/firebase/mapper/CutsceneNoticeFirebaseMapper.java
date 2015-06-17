package com.shinav.mathapp.firebase.mapper;

import com.firebase.client.DataSnapshot;
import com.shinav.mathapp.db.pojo.CutsceneNotice;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.ALIGNMENT;
import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.CUTSCENE_KEY;
import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.POSITION;
import static com.shinav.mathapp.db.helper.Tables.CutsceneNotice.TEXT;

public class CutsceneNoticeFirebaseMapper extends FirebaseMapper<CutsceneNotice> {

    @Inject public CutsceneNoticeFirebaseMapper() { }

    @Override public CutsceneNotice fromDataSnapshot(DataSnapshot dataSnapshot) {
        CutsceneNotice cutsceneNotice = new CutsceneNotice();

        String cutsceneKey =    getString(dataSnapshot, CUTSCENE_KEY);
        String text =           getString(dataSnapshot, TEXT);
        String position =       getString(dataSnapshot, POSITION);
        String alignment =      getString(dataSnapshot, ALIGNMENT);
        String imageUrl =       getString(dataSnapshot, IMAGE_URL);

        cutsceneNotice.setKey(dataSnapshot.getKey());
        cutsceneNotice.setCutsceneKey(cutsceneKey);
        cutsceneNotice.setText(text);
        cutsceneNotice.setPosition(Integer.parseInt(position));
        cutsceneNotice.setAlignment(Integer.parseInt(alignment));
        cutsceneNotice.setImageUrl(imageUrl);

        return cutsceneNotice;
    }

}

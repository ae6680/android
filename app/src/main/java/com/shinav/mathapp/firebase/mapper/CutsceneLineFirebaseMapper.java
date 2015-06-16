package com.shinav.mathapp.firebase.mapper;

import com.firebase.client.DataSnapshot;
import com.shinav.mathapp.db.pojo.CutsceneLine;

import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.ALIGNMENT;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.CUTSCENE_KEY;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.DELAY;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.MAIN_CHARACTER;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.POSITION;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.TYPING_DURATION;
import static com.shinav.mathapp.db.helper.Tables.CutsceneLine.VALUE;

public class CutsceneLineFirebaseMapper extends FirebaseMapper<CutsceneLine> {

    @Override public CutsceneLine fromDataSnapshot(DataSnapshot dataSnapshot) {
        CutsceneLine cutsceneLine = new CutsceneLine();

        String cutsceneKey =    getString(dataSnapshot, CUTSCENE_KEY);
        String value =          getString(dataSnapshot, VALUE);
        String position =       getString(dataSnapshot, POSITION);
        String delay =          getString(dataSnapshot, DELAY);
        String typingDuration = getString(dataSnapshot, TYPING_DURATION);
        String alignment =      getString(dataSnapshot, ALIGNMENT);
        String imageUrl =       getString(dataSnapshot, IMAGE_URL);
        String mainCharacter =  getString(dataSnapshot, MAIN_CHARACTER);

        cutsceneLine.setKey(dataSnapshot.getKey());
        cutsceneLine.setCutsceneKey(cutsceneKey);
        cutsceneLine.setValue(value);
        cutsceneLine.setPosition(Integer.parseInt(position));
        cutsceneLine.setDelay(Integer.parseInt(delay));
        cutsceneLine.setTypingDuration(Integer.parseInt(typingDuration));
        cutsceneLine.setAlignment(Integer.parseInt(alignment));
        cutsceneLine.setImageUrl(imageUrl);
        cutsceneLine.setMainCharacter(Integer.parseInt(mainCharacter));

        return cutsceneLine;
    }

}

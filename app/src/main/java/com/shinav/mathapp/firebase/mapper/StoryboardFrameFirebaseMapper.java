package com.shinav.mathapp.firebase.mapper;

import com.firebase.client.DataSnapshot;
import com.shinav.mathapp.db.pojo.StoryboardFrame;

import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.FRAME_TYPE;
import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.FRAME_TYPE_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.POSITION;
import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.STORYBOARD_KEY;

public class StoryboardFrameFirebaseMapper extends FirebaseMapper<StoryboardFrame> {

    @Override public StoryboardFrame fromDataSnapshot(DataSnapshot dataSnapshot) {
        StoryboardFrame storyboardFrame = new StoryboardFrame();

        String position = getString(dataSnapshot, POSITION);
        String type =     getString(dataSnapshot, FRAME_TYPE);
        String typeKey =  getString(dataSnapshot, FRAME_TYPE_KEY);
        String storyKey = getString(dataSnapshot, STORYBOARD_KEY);

        storyboardFrame.setKey(dataSnapshot.getKey());
        storyboardFrame.setStoryboardKey(storyKey);
        storyboardFrame.setPosition(Integer.parseInt(position));
        storyboardFrame.setFrameType(type);
        storyboardFrame.setFrameTypeKey(typeKey);

        return storyboardFrame;
    }

}

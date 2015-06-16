package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.TutorialFrame;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.FRAME_TYPE;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.FRAME_TYPE_KEY;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.KEY;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.POSITION;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.TUTORIAL_KEY;

public class TutorialFrameDataMapper extends DataMapper<TutorialFrame> {

    @Inject
    public TutorialFrameDataMapper() { }

    @Override public ContentValues getContentValues(TutorialFrame tutorialFrame) {
        ContentValues values = new ContentValues();

        values.put(KEY, tutorialFrame.getKey());
        values.put(TUTORIAL_KEY, tutorialFrame.getTutorialKey());
        values.put(FRAME_TYPE, tutorialFrame.getFrameType());
        values.put(FRAME_TYPE_KEY, tutorialFrame.getFrameTypeKey());
        values.put(POSITION, tutorialFrame.getPosition());

        return values;
    }

    @Override public String getTable() {
        return TABLE_NAME;
    }

}

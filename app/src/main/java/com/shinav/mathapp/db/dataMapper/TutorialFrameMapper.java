package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.TutorialFrame;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.FRAME_TYPE;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.FRAME_TYPE_KEY;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.KEY;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.POSITION;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.TUTORIAL_KEY;

public class TutorialFrameMapper {

    @Inject SqlBrite db;

    @Inject
    public TutorialFrameMapper() { }

    private ContentValues getContentValues(TutorialFrame tutorialFrame) {
        ContentValues values = new ContentValues();

        values.put(KEY, tutorialFrame.getKey());
        values.put(TUTORIAL_KEY, tutorialFrame.getTutorialKey());
        values.put(FRAME_TYPE, tutorialFrame.getFrameType());
        values.put(FRAME_TYPE_KEY, tutorialFrame.getFrameTypeKey());
        values.put(POSITION, tutorialFrame.getPosition());

        return values;
    }

    public void insert(TutorialFrame tutorialFrame) {
        db.insert(TABLE_NAME, getContentValues(tutorialFrame));
    }

    public void update(TutorialFrame tutorialFrame) {
        db.update(
                TABLE_NAME,
                getContentValues(tutorialFrame),
                KEY + " = ?",
                tutorialFrame.getKey()
        );
    }

    public void delete(String tutorialFrameKey) {
        db.delete(
                TABLE_NAME,
                KEY + " = ?",
                tutorialFrameKey
        );
    }

}

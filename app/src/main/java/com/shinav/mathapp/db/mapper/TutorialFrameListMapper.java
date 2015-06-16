package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.TutorialFrame;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.FRAME_TYPE;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.FRAME_TYPE_KEY;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.KEY;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.POSITION;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.TUTORIAL_KEY;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class TutorialFrameListMapper extends ListMapper<TutorialFrame> {

    @Inject
    public TutorialFrameListMapper() { }

    @Override public List<TutorialFrame> call(Query query) {
        List<TutorialFrame> tutorialFrames = super.call(query);
        Collections.sort(tutorialFrames);

        return tutorialFrames;
    }

    @Override public TutorialFrame fromCursor(Cursor c) {
        TutorialFrame tutorialFrame = new TutorialFrame();

        tutorialFrame.setKey(c.getString(c.getColumnIndex(KEY)));
        tutorialFrame.setTutorialKey(c.getString(c.getColumnIndex(TUTORIAL_KEY)));
        tutorialFrame.setPosition(c.getInt(c.getColumnIndex(POSITION)));
        tutorialFrame.setFrameType(c.getString(c.getColumnIndex(FRAME_TYPE)));
        tutorialFrame.setFrameTypeKey(c.getString(c.getColumnIndex(FRAME_TYPE_KEY)));

        return tutorialFrame;
    }

}

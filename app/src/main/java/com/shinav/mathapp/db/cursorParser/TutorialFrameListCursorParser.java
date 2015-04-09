package com.shinav.mathapp.db.cursorParser;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.TutorialFrame;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.FRAME_TYPE;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.FRAME_TYPE_KEY;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.KEY;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.POSITION;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.TUTORIAL_KEY;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class TutorialFrameListCursorParser implements Func1<Query, List<TutorialFrame>> {

    @Inject
    public TutorialFrameListCursorParser() {
    }

    @Override public List<TutorialFrame> call(Query query) {
        Cursor c = query.run();
        try {
            List<TutorialFrame> tutorialFrames = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                tutorialFrames.add(fromCursor(c));
            }
            return tutorialFrames;
        } finally {
            c.close();
        }
    }

    public TutorialFrame fromCursor(Cursor c) {
        TutorialFrame tutorialFrame = new TutorialFrame();

        tutorialFrame.setKey(c.getString(c.getColumnIndex(KEY)));
        tutorialFrame.setTutorialKey(c.getString(c.getColumnIndex(TUTORIAL_KEY)));
        tutorialFrame.setPosition(c.getInt(c.getColumnIndex(POSITION)));
        tutorialFrame.setFrameType(c.getString(c.getColumnIndex(FRAME_TYPE)));
        tutorialFrame.setFrameTypeKey(c.getString(c.getColumnIndex(FRAME_TYPE_KEY)));

        return tutorialFrame;
    }

}

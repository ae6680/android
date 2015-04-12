package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.cursorParser.StoryboardFrameCursorParser;
import com.shinav.mathapp.db.pojo.StoryboardFrame;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.FRAME_TYPE;
import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.STORYBOARD_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.TABLE_NAME;

public class StoryboardFrameRepository {

    public static String FRAME_TYPE_QUESTION = "question";
    public static String FRAME_TYPE_CONVERSATION = "conversation";

    @Inject SqlBrite db;
    @Inject StoryboardFrameCursorParser parser;

    @Inject
    public StoryboardFrameRepository() { }

    public Observable<List<StoryboardFrame>> getByStoryboardKey(String storyboardKey) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + STORYBOARD_KEY + " = ?",
                storyboardKey
        ).map(parser);
    }

    public Observable<List<StoryboardFrame>> getQuestionFrames(String storyboardKey) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + STORYBOARD_KEY + " = ?" +
                        " AND " + FRAME_TYPE + " = ?"
                , storyboardKey
                , FRAME_TYPE_QUESTION
        ).map(parser);
    }
}

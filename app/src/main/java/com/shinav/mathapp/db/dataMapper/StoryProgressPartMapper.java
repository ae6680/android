package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;
import android.database.Cursor;

import com.shinav.mathapp.db.pojo.StoryProgressPart;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.QUESTION_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.STATE;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.STORY_PROGRESS_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart.TITLE;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class StoryProgressPartMapper {

    private final SqlBrite db;
    private Func1<Query, StoryProgressPart> singleFunction;
    private Func1<Query, List<StoryProgressPart>> collectionFunction;

    @Inject
    public StoryProgressPartMapper(SqlBrite db) {
        this.db = db;
        initFunctions();
    }

    private void initFunctions() {
        singleFunction = new Func1<Query, StoryProgressPart>() {
            @Override public StoryProgressPart call(Query query) {
                Cursor c = query.run();
                try {
                    if (!c.moveToFirst()) {
                        return null;
                    }

                    return fromCursor(c);
                } finally {
                    c.close();
                }
            }
        };

        collectionFunction = new Func1<Query, List<StoryProgressPart>>() {
            @Override public List<StoryProgressPart> call(Query query) {
                Cursor c = query.run();
                try {
                    List<StoryProgressPart> storyProgressParts = new ArrayList<>(c.getCount());
                    while (c.moveToNext()) {
                        storyProgressParts.add(fromCursor(c));
                    }
                    return storyProgressParts;
                } finally {
                    c.close();
                }
            }
        };
    }

    public StoryProgressPart fromCursor(Cursor c) {
        StoryProgressPart storyProgressPart = new StoryProgressPart();

        storyProgressPart.setKey(c.getString(c.getColumnIndex(KEY)));
        storyProgressPart.setStoryProgressKey(c.getString(c.getColumnIndex(STORY_PROGRESS_KEY)));
        storyProgressPart.setQuestionKey(c.getString(c.getColumnIndex(QUESTION_KEY)));
        storyProgressPart.setTitle(c.getString(c.getColumnIndex(TITLE)));
        storyProgressPart.setState(c.getInt(c.getColumnIndex(STATE)));

        return storyProgressPart;
    }

    private ContentValues getContentValues(StoryProgressPart part) {
        ContentValues values = new ContentValues();

        values.put(KEY, part.getKey());
        values.put(STORY_PROGRESS_KEY, part.getStoryProgressKey());
        values.put(QUESTION_KEY, part.getQuestionKey());
        values.put(TITLE, part.getTitle());
        values.put(STATE, part.getState());

        return values;
    }

    public Subscription getByQuestionKey(String questionKey, Action1<StoryProgressPart> action) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + QUESTION_KEY + " = ?"
                , questionKey
        )
                .map(singleFunction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action);
    }

    public Subscription getByStoryProgressKey(String storyProgressKey, Action1<List<StoryProgressPart>> action) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + STORY_PROGRESS_KEY + " = ?"
                , storyProgressKey
        )
                .map(collectionFunction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action);
    }

    public void insert(StoryProgressPart part) {
        db.insert(TABLE_NAME, getContentValues(part));
    }

    public void update(StoryProgressPart part) {
        db.update(
                TABLE_NAME,
                getContentValues(part),
                KEY + " = ?",
                part.getKey()
        );
    }

}

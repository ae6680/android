package com.shinav.mathapp.db.mapper;

import android.content.ContentValues;
import android.database.Cursor;

import com.shinav.mathapp.db.pojo.StoryPart;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static com.shinav.mathapp.db.helper.Tables.StoryPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryPart.POSITION;
import static com.shinav.mathapp.db.helper.Tables.StoryPart.STORY_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryPart.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.StoryPart.TYPE;
import static com.shinav.mathapp.db.helper.Tables.StoryPart.TYPE_KEY;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class StoryPartMapper implements rx.functions.Func1<Query, List<StoryPart>> {

    @Inject SqlBrite db;

    @Inject
    public StoryPartMapper() { }

    @Override public List<StoryPart> call(Query query) {
        Cursor c = query.run();
        try {
            List<StoryPart> storyParts = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                storyParts.add(fromCursor(c));
            }
            return storyParts;
        } finally {
            c.close();
        }
    }

    public StoryPart fromCursor(Cursor c) {
        StoryPart storyPart = new StoryPart();

        storyPart.setKey(c.getString(c.getColumnIndex(KEY)));
        storyPart.setStoryKey(c.getString(c.getColumnIndex(STORY_KEY)));
        storyPart.setPosition(c.getInt(c.getColumnIndex(POSITION)));
        storyPart.setType(c.getString(c.getColumnIndex(TYPE)));
        storyPart.setTypeKey(c.getString(c.getColumnIndex(TYPE_KEY)));

        return storyPart;
    }

    private ContentValues getContentValues(StoryPart storyPart) {
        ContentValues values = new ContentValues();

        values.put(KEY, storyPart.getKey());
        values.put(STORY_KEY, storyPart.getStoryKey());
        values.put(TYPE, storyPart.getType());
        values.put(TYPE_KEY, storyPart.getTypeKey());
        values.put(POSITION, storyPart.getPosition());

        return values;
    }

    public Subscription getByStoryKey(String storyKey, Action1<List<StoryPart>> action) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + STORY_KEY + " = ?"
                , storyKey
        ).map(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action);
    }

    public void insert(StoryPart storyPart) {
        db.insert(TABLE_NAME, getContentValues(storyPart));
    }

}

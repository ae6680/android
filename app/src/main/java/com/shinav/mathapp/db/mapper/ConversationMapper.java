package com.shinav.mathapp.db.mapper;

import android.content.ContentValues;
import android.database.Cursor;

import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.Conversation;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static com.shinav.mathapp.db.helper.Tables.Conversation.KEY;
import static com.shinav.mathapp.db.helper.Tables.Conversation.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.Conversation.TITLE;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class ConversationMapper implements rx.functions.Func1<Query, Conversation> {

    @Inject SqlBrite db;

    @Inject
    public ConversationMapper() {
    }

    @Override public Conversation call(Query query) {
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

    private Conversation fromCursor(Cursor c) {
        Conversation conversation = new Conversation();

        conversation.setKey(c.getString(c.getColumnIndex(KEY)));
        conversation.setTitle(c.getString(c.getColumnIndex(TITLE)));

        return conversation;
    }

    public ContentValues getContentValues(Conversation conversation) {
        ContentValues values = new ContentValues();

        values.put(Tables.Conversation.KEY, conversation.getKey());
        values.put(Tables.Conversation.TITLE, conversation.getTitle());

        return values;
    }

    public void insert(Conversation conversation) {
        db.insert(TABLE_NAME, getContentValues(conversation));
    }

    public Subscription getByKey(String conversationKey, Action1<Conversation> action) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + KEY + " = ?"
                , conversationKey
        )
                .map(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action);
    }

}

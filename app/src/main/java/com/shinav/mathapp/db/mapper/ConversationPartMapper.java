package com.shinav.mathapp.db.mapper;

import android.content.ContentValues;
import android.database.Cursor;

import com.shinav.mathapp.db.pojo.ConversationPart;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static com.shinav.mathapp.db.helper.Tables.ConversationPart.ALIGNMENT;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.CONVERSATION_KEY;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.DELAY;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.MESSAGE;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.POSITION;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.TYPING_DURATION;
import static com.squareup.sqlbrite.SqlBrite.Query;

public class ConversationPartMapper implements rx.functions.Func1<Query, List<ConversationPart>> {

    @Inject SqlBrite db;

    @Inject
    public ConversationPartMapper() { }

    @Override public List<ConversationPart> call(Query query) {
        Cursor c = query.run();
        try {
            List<ConversationPart> conversationParts = new ArrayList<>(c.getCount());
            while (c.moveToNext()) {
                conversationParts.add(fromCursor(c));
            }
            return conversationParts;
        } finally {
            c.close();
        }
    }

    public ConversationPart fromCursor(Cursor c) {
        ConversationPart conversationPart = new ConversationPart();

        conversationPart.setKey(c.getString(c.getColumnIndex(KEY)));
        conversationPart.setConversationKey(c.getString(c.getColumnIndex(CONVERSATION_KEY)));
        conversationPart.setMessage(c.getString(c.getColumnIndex(MESSAGE)));
        conversationPart.setPosition(c.getInt(c.getColumnIndex(POSITION)));
        conversationPart.setDelay(c.getInt(c.getColumnIndex(DELAY)));
        conversationPart.setTypingDuration(c.getInt(c.getColumnIndex(TYPING_DURATION)));
        conversationPart.setAlignment(c.getInt(c.getColumnIndex(ALIGNMENT)));

        return conversationPart;
    }

    private ContentValues getContentValues(ConversationPart conversationPart) {
        ContentValues values = new ContentValues();

        values.put(KEY, conversationPart.getKey());
        values.put(POSITION, conversationPart.getPosition());
        values.put(ALIGNMENT, conversationPart.getAlignment());
        values.put(DELAY, conversationPart.getDelay());
        values.put(TYPING_DURATION, conversationPart.getTypingDuration());
        values.put(CONVERSATION_KEY, conversationPart.getConversationKey());
        values.put(MESSAGE, conversationPart.getMessage());

        return values;
    }

    public void insert(ConversationPart conversationPart) {
        db.insert(TABLE_NAME, getContentValues(conversationPart));
    }

    public Subscription getByConversationKey(String conversationKey, Action1<List<ConversationPart>> action) {
        return db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + CONVERSATION_KEY + " = ?"
                , conversationKey
        )
                .map(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action);
    }
}

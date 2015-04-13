package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.Conversation;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Conversation.BACKGROUND_IMAGE_URL;
import static com.shinav.mathapp.db.helper.Tables.Conversation.KEY;
import static com.shinav.mathapp.db.helper.Tables.Conversation.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.Conversation.TITLE;

public class ConversationMapper {

    @Inject SqlBrite db;

    @Inject
    public ConversationMapper() { }

    private ContentValues getContentValues(Conversation conversation) {
        ContentValues values = new ContentValues();

        values.put(KEY, conversation.getKey());
        values.put(TITLE, conversation.getTitle());
        values.put(BACKGROUND_IMAGE_URL, conversation.getBackgroundImageUrl());

        return values;
    }

    public void insert(Conversation conversation) {
        db.insert(TABLE_NAME, getContentValues(conversation));
    }

    public void update(Conversation conversation) {
        db.update(
                TABLE_NAME,
                getContentValues(conversation),
                KEY + " = ?",
                conversation.getKey()
        );
    }

    public void delete(String conversationKey) {
        db.delete(
                TABLE_NAME,
                KEY + " = ?",
                conversationKey
        );
    }

}

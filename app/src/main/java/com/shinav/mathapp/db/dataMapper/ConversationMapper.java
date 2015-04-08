package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.Conversation;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Conversation.TABLE_NAME;

public class ConversationMapper {

    @Inject SqlBrite db;

    @Inject
    public ConversationMapper() { }

    private ContentValues getContentValues(Conversation conversation) {
        ContentValues values = new ContentValues();

        values.put(Tables.Conversation.KEY, conversation.getKey());
        values.put(Tables.Conversation.TITLE, conversation.getTitle());

        return values;
    }

    public void insert(Conversation conversation) {
        db.insert(TABLE_NAME, getContentValues(conversation));
    }

}

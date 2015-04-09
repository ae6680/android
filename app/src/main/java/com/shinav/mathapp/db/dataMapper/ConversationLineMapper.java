package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.ConversationLine;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.ConversationLine.ALIGNMENT;
import static com.shinav.mathapp.db.helper.Tables.ConversationLine.CONVERSATION_KEY;
import static com.shinav.mathapp.db.helper.Tables.ConversationLine.DELAY;
import static com.shinav.mathapp.db.helper.Tables.ConversationLine.KEY;
import static com.shinav.mathapp.db.helper.Tables.ConversationLine.POSITION;
import static com.shinav.mathapp.db.helper.Tables.ConversationLine.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.ConversationLine.TYPING_DURATION;
import static com.shinav.mathapp.db.helper.Tables.ConversationLine.VALUE;

public class ConversationLineMapper {

    @Inject SqlBrite db;

    @Inject
    public ConversationLineMapper() { }

    private ContentValues getContentValues(ConversationLine conversationLine) {
        ContentValues values = new ContentValues();

        values.put(KEY, conversationLine.getKey());
        values.put(POSITION, conversationLine.getPosition());
        values.put(ALIGNMENT, conversationLine.getAlignment());
        values.put(DELAY, conversationLine.getDelay());
        values.put(TYPING_DURATION, conversationLine.getTypingDuration());
        values.put(CONVERSATION_KEY, conversationLine.getConversationKey());
        values.put(VALUE, conversationLine.getValue());

        return values;
    }

    public void insert(ConversationLine conversationLine) {
        db.insert(TABLE_NAME, getContentValues(conversationLine));
    }

}

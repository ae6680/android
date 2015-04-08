package com.shinav.mathapp.db.dataMapper;

import android.content.ContentValues;

import com.shinav.mathapp.db.pojo.ConversationPart;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.ConversationPart.ALIGNMENT;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.CONVERSATION_KEY;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.DELAY;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.KEY;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.MESSAGE;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.POSITION;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart.TYPING_DURATION;

public class ConversationPartMapper {

    @Inject SqlBrite db;

    @Inject
    public ConversationPartMapper() { }

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

}

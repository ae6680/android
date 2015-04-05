package com.shinav.mathapp.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shinav.mathapp.injection.annotation.ForApplication;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Approach;
import static com.shinav.mathapp.db.helper.Tables.ApproachPart;
import static com.shinav.mathapp.db.helper.Tables.Conversation;
import static com.shinav.mathapp.db.helper.Tables.ConversationPart;
import static com.shinav.mathapp.db.helper.Tables.Question;
import static com.shinav.mathapp.db.helper.Tables.Story;
import static com.shinav.mathapp.db.helper.Tables.StoryPart;
import static com.shinav.mathapp.db.helper.Tables.StoryProgress;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart;

public class DbOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "math_app_db";

    private static final int CURRENT_VERSION = 1;

    @Inject
    public DbOpenHelper(@ForApplication Context context) {
        super(context, DB_NAME, null, CURRENT_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        createTables(db);
        addMigrations();
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createTables(SQLiteDatabase db) {
        createQuestionTable(db);

        createApproachTable(db);
        createApproachPartTable(db);

        createStoryTable(db);
        createStoryPartTable(db);

        createConversationTable(db);
        createConversationPartTable(db);

        createStoryProgressTable(db);
        createStoryProgressPartTable(db);
    }

    private void addMigrations() {

    }

    private void createQuestionTable(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + Question.TABLE_NAME + " ("
                        + Question.KEY + " TEXT,"
                        + Question.TITLE + " TEXT,"
                        + Question.VALUE + " TEXT,"
                        + Question.ANSWER + " TEXT,"
                        + " UNIQUE (" + Question.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, Question.TABLE_NAME, Question.KEY);
    }

    private void createApproachTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Approach.TABLE_NAME + " ("
                        + Approach.KEY + " TEXT,"
                        + Approach.QUESTION_KEY + " TEXT,"
                        + " UNIQUE (" + Approach.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, Approach.TABLE_NAME, Approach.KEY);
    }

    private void createApproachPartTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ApproachPart.TABLE_NAME + " ("
                        + ApproachPart.KEY + " TEXT,"
                        + ApproachPart.APPROACH_KEY + " TEXT,"
                        + ApproachPart.VALUE + " TEXT,"
                        + ApproachPart.POSITION + " INTEGER,"
                        + " UNIQUE (" + ApproachPart.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, ApproachPart.TABLE_NAME, ApproachPart.KEY);
    }

    private void createStoryTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Story.TABLE_NAME + " ("
                        + Story.KEY + " TEXT,"
                        + " UNIQUE (" + Story.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, Story.TABLE_NAME, Story.KEY);
    }

    private void createStoryPartTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + StoryPart.TABLE_NAME + " ("
                        + StoryPart.KEY + " TEXT,"
                        + StoryPart.STORY_KEY + " TEXT,"
                        + StoryPart.TYPE + " TEXT,"
                        + StoryPart.TYPE_KEY + " TEXT,"
                        + StoryPart.POSITION + " INTEGER,"
                        + " UNIQUE (" + StoryPart.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, StoryPart.TABLE_NAME, StoryPart.KEY);
    }

    private void createConversationTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Conversation.TABLE_NAME + " ("
                        + Conversation.KEY + " TEXT,"
                        + Conversation.TITLE + " TEXT,"
                        + " UNIQUE (" + Conversation.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, Conversation.TABLE_NAME, Conversation.KEY);
    }

    private void createConversationPartTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ConversationPart.TABLE_NAME + " ("
                        + ConversationPart.KEY + " TEXT,"
                        + ConversationPart.CONVERSATION_KEY + " TEXT,"
                        + ConversationPart.MESSAGE + " TEXT,"
                        + ConversationPart.DELAY + " INTEGER,"
                        + ConversationPart.TYPING_DURATION + " INTEGER,"
                        + ConversationPart.POSITION + " INTEGER,"
                        + ConversationPart.ALIGNMENT + " INTEGER,"
                        + " UNIQUE (" + ConversationPart.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, ConversationPart.TABLE_NAME, ConversationPart.KEY);
    }

    private void createStoryProgressTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + StoryProgress.TABLE_NAME + " ("
                        + StoryProgress.KEY + " TEXT,"
                        + " UNIQUE (" + StoryProgress.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, StoryProgress.TABLE_NAME, StoryProgress.KEY);
    }

    private void createStoryProgressPartTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + StoryProgressPart.TABLE_NAME + " ("
                        + StoryProgressPart.KEY + " TEXT,"
                        + StoryProgressPart.STORY_PROGRESS_KEY + " TEXT,"
                        + StoryProgressPart.QUESTION_KEY + " TEXT,"
                        + StoryProgressPart.TITLE + " TEXT,"
                        + StoryProgressPart.STATE + " INTEGER,"
                        + " UNIQUE (" + StoryProgressPart.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, StoryProgressPart.TABLE_NAME, StoryProgressPart.KEY);
    }

    private void createIndex(SQLiteDatabase db, String tableName, String column) {
        db.execSQL("CREATE INDEX " + tableName + column +
                        " ON " + tableName + " (" + column + ")"
        );
    }

}

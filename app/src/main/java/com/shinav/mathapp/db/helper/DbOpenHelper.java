package com.shinav.mathapp.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shinav.mathapp.injection.annotation.ForApplication;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Conversation;
import static com.shinav.mathapp.db.helper.Tables.ConversationLine;
import static com.shinav.mathapp.db.helper.Tables.GivenAnswer;
import static com.shinav.mathapp.db.helper.Tables.GivenQuestionApproach;
import static com.shinav.mathapp.db.helper.Tables.Question;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproach;
import static com.shinav.mathapp.db.helper.Tables.QuestionApproachPart;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation;
import static com.shinav.mathapp.db.helper.Tables.Storyboard;
import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame;
import static com.shinav.mathapp.db.helper.Tables.Tutorial;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame;

public class DbOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "math_app_db";

    private static final int CURRENT_VERSION = 1;

    @Inject
    public DbOpenHelper(@ForApplication Context context) {
        super(context, DB_NAME, null, CURRENT_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        createTables(db);
        addMigrations(db);
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createTables(SQLiteDatabase db) {
        createQuestionTable(db);
        createGivenAnswerTable(db);

        createQuestionApproachTable(db);
        createQuestionApproachPartTable(db);
        createGivenApproachTable(db);

        createQuestionExplanationTable(db);

        createStoryboardTable(db);
        createStoryboardFrameTable(db);

        createConversationTable(db);
        createConversationLineTable(db);

        createTutorialTable(db);
        createTutorialFrameTable(db);
    }

    private void addMigrations(SQLiteDatabase db) {

    }

    private void createQuestionTable(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + Question.TABLE_NAME + " ("
                        + Question.KEY + " TEXT,"
                        + Question.TITLE + " TEXT,"
                        + Question.VALUE + " TEXT,"
                        + Question.ANSWER + " TEXT,"
                        + Question.ANNEX_IMAGE_URL + " TEXT,"
                        + Question.BACKGROUND_IMAGE_URL + " TEXT,"
                        + Question.PROGRESS_STATE + " INTEGER,"
                        + " UNIQUE (" + Question.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, Question.TABLE_NAME, Question.KEY);
    }

    private void createQuestionApproachTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + QuestionApproach.TABLE_NAME + " ("
                        + QuestionApproach.KEY + " TEXT,"
                        + QuestionApproach.QUESTION_KEY + " TEXT,"
                        + " UNIQUE (" + QuestionApproach.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, QuestionApproach.TABLE_NAME, QuestionApproach.KEY);
    }

    private void createQuestionApproachPartTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + QuestionApproachPart.TABLE_NAME + " ("
                        + QuestionApproachPart.KEY + " TEXT,"
                        + QuestionApproachPart.QUESTION_APPROACH_KEY + " TEXT,"
                        + QuestionApproachPart.VALUE + " TEXT,"
                        + QuestionApproachPart.POSITION + " INTEGER,"
                        + " UNIQUE (" + QuestionApproachPart.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, QuestionApproachPart.TABLE_NAME, QuestionApproachPart.KEY);
    }

    private void createGivenApproachTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + GivenQuestionApproach.TABLE_NAME + " ("
                        + GivenQuestionApproach.KEY + " TEXT,"
                        + GivenQuestionApproach.QUESTION_APPROACH_KEY + " TEXT,"
                        + GivenQuestionApproach.ARRANGEMENT + " TEXT,"
                        + GivenQuestionApproach.GIVEN_AT + " INTEGER,"
                        + " UNIQUE (" + GivenQuestionApproach.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, GivenQuestionApproach.TABLE_NAME, GivenQuestionApproach.KEY);
    }

    private void createGivenAnswerTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + GivenAnswer.TABLE_NAME + " ("
                        + GivenAnswer.KEY + " TEXT,"
                        + GivenAnswer.QUESTION_KEY + " TEXT,"
                        + GivenAnswer.VALUE + " TEXT,"
                        + GivenAnswer.GIVEN_AT + " INTEGER,"
                        + " UNIQUE (" + GivenAnswer.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, GivenAnswer.TABLE_NAME, GivenAnswer.KEY);
    }

    private void createQuestionExplanationTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + QuestionExplanation.TABLE_NAME + " ("
                        + QuestionExplanation.KEY + " TEXT,"
                        + QuestionExplanation.QUESTION_KEY + " TEXT,"
                        + QuestionExplanation.TEXT + " TEXT,"
                        + QuestionExplanation.IMAGE_URL + " TEXT,"
                        + QuestionExplanation.POSITION + " INTEGER,"
                        + " UNIQUE (" + QuestionExplanation.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, QuestionExplanation.TABLE_NAME, QuestionExplanation.KEY);
    }

    private void createStoryboardTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Storyboard.TABLE_NAME + " ("
                        + Storyboard.KEY + " TEXT,"
                        + Storyboard.TITLE + " TEXT,"
                        + " UNIQUE (" + Storyboard.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, Storyboard.TABLE_NAME, Storyboard.KEY);
    }

    private void createStoryboardFrameTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + StoryboardFrame.TABLE_NAME + " ("
                        + StoryboardFrame.KEY + " TEXT,"
                        + StoryboardFrame.STORYBOARD_KEY + " TEXT,"
                        + StoryboardFrame.FRAME_TYPE + " TEXT,"
                        + StoryboardFrame.FRAME_TYPE_KEY + " TEXT,"
                        + StoryboardFrame.POSITION + " INTEGER,"
                        + " UNIQUE (" + StoryboardFrame.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, StoryboardFrame.TABLE_NAME, StoryboardFrame.KEY);
    }

    private void createConversationTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Conversation.TABLE_NAME + " ("
                        + Conversation.KEY + " TEXT,"
                        + Conversation.TITLE + " TEXT,"
                        + Conversation.BACKGROUND_IMAGE_URL + " TEXT,"
                        + " UNIQUE (" + Conversation.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, Conversation.TABLE_NAME, Conversation.KEY);
    }

    private void createConversationLineTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ConversationLine.TABLE_NAME + " ("
                        + ConversationLine.KEY + " TEXT,"
                        + ConversationLine.CONVERSATION_KEY + " TEXT,"
                        + ConversationLine.VALUE + " TEXT,"
                        + ConversationLine.DELAY + " INTEGER,"
                        + ConversationLine.TYPING_DURATION + " INTEGER,"
                        + ConversationLine.POSITION + " INTEGER,"
                        + ConversationLine.ALIGNMENT + " INTEGER,"
                        + ConversationLine.IMAGE_URL + " TEXT,"
                        + " UNIQUE (" + ConversationLine.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, ConversationLine.TABLE_NAME, ConversationLine.KEY);
    }

    private void createTutorialTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tutorial.TABLE_NAME + " ("
                        + Tutorial.KEY + " TEXT,"
                        + Tutorial.TITLE + " TEXT,"
                        + " UNIQUE (" + Tutorial.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, Tutorial.TABLE_NAME, Tutorial.KEY);
    }

    private void createTutorialFrameTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TutorialFrame.TABLE_NAME + " ("
                        + TutorialFrame.KEY + " TEXT,"
                        + TutorialFrame.TUTORIAL_KEY + " TEXT,"
                        + TutorialFrame.FRAME_TYPE + " TEXT,"
                        + TutorialFrame.FRAME_TYPE_KEY + " TEXT,"
                        + TutorialFrame.POSITION + " INTEGER,"
                        + " UNIQUE (" + TutorialFrame.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, TutorialFrame.TABLE_NAME, TutorialFrame.KEY);
    }

    private void createIndex(SQLiteDatabase db, String tableName, String column) {
        db.execSQL("CREATE INDEX " + tableName + column +
                        " ON " + tableName + " (" + column + ")"
        );
    }

}

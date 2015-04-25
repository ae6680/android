package com.shinav.mathapp.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shinav.mathapp.injection.annotation.ForApplication;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.Conversation;
import static com.shinav.mathapp.db.helper.Tables.GivenAnswer;
import static com.shinav.mathapp.db.helper.Tables.Question;
import static com.shinav.mathapp.db.helper.Tables.QuestionExplanation;
import static com.shinav.mathapp.db.helper.Tables.StoryProgress;
import static com.shinav.mathapp.db.helper.Tables.StoryProgressPart;
import static com.shinav.mathapp.db.helper.Tables.Tutorial;

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

        createStoryProgressTable(db);
        createStoryProgressPartTable(db);

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
        db.execSQL("CREATE TABLE " + Tables.QuestionApproach.TABLE_NAME + " ("
                        + Tables.QuestionApproach.KEY + " TEXT,"
                        + Tables.QuestionApproach.QUESTION_KEY + " TEXT,"
                        + " UNIQUE (" + Tables.QuestionApproach.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, Tables.QuestionApproach.TABLE_NAME, Tables.QuestionApproach.KEY);
    }

    private void createQuestionApproachPartTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.QuestionApproachPart.TABLE_NAME + " ("
                        + Tables.QuestionApproachPart.KEY + " TEXT,"
                        + Tables.QuestionApproachPart.QUESTION_APPROACH_KEY + " TEXT,"
                        + Tables.QuestionApproachPart.VALUE + " TEXT,"
                        + Tables.QuestionApproachPart.POSITION + " INTEGER,"
                        + " UNIQUE (" + Tables.QuestionApproachPart.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, Tables.QuestionApproachPart.TABLE_NAME, Tables.QuestionApproachPart.KEY);
    }

    private void createGivenApproachTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.GivenQuestionApproach.TABLE_NAME + " ("
                        + Tables.GivenQuestionApproach.KEY + " TEXT,"
                        + Tables.GivenQuestionApproach.QUESTION_APPROACH_KEY + " TEXT,"
                        + Tables.GivenQuestionApproach.ARRANGEMENT + " TEXT,"
                        + Tables.GivenQuestionApproach.GIVEN_AT + " INTEGER,"
                        + " UNIQUE (" + Tables.GivenQuestionApproach.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, Tables.GivenQuestionApproach.TABLE_NAME, Tables.GivenQuestionApproach.KEY);
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
        db.execSQL("CREATE TABLE " + Tables.Storyboard.TABLE_NAME + " ("
                        + Tables.Storyboard.KEY + " TEXT,"
                        + Tables.Storyboard.TITLE + " TEXT,"
                        + " UNIQUE (" + Tables.Storyboard.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, Tables.Storyboard.TABLE_NAME, Tables.Storyboard.KEY);
    }

    private void createStoryboardFrameTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.StoryboardFrame.TABLE_NAME + " ("
                        + Tables.StoryboardFrame.KEY + " TEXT,"
                        + Tables.StoryboardFrame.STORYBOARD_KEY + " TEXT,"
                        + Tables.StoryboardFrame.FRAME_TYPE + " TEXT,"
                        + Tables.StoryboardFrame.FRAME_TYPE_KEY + " TEXT,"
                        + Tables.StoryboardFrame.POSITION + " INTEGER,"
                        + " UNIQUE (" + Tables.StoryboardFrame.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, Tables.StoryboardFrame.TABLE_NAME, Tables.StoryboardFrame.KEY);
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
        db.execSQL("CREATE TABLE " + Tables.ConversationLine.TABLE_NAME + " ("
                        + Tables.ConversationLine.KEY + " TEXT,"
                        + Tables.ConversationLine.CONVERSATION_KEY + " TEXT,"
                        + Tables.ConversationLine.VALUE + " TEXT,"
                        + Tables.ConversationLine.DELAY + " INTEGER,"
                        + Tables.ConversationLine.TYPING_DURATION + " INTEGER,"
                        + Tables.ConversationLine.POSITION + " INTEGER,"
                        + Tables.ConversationLine.ALIGNMENT + " INTEGER,"
                        + Tables.ConversationLine.IMAGE_URL + " TEXT,"
                        + " UNIQUE (" + Tables.ConversationLine.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, Tables.ConversationLine.TABLE_NAME, Tables.ConversationLine.KEY);
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

    private void createTutorialTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tutorial.TABLE_NAME + " ("
                        + Tutorial.KEY + " TEXT,"
                        + Tutorial.TITLE + " TEXT,"
                        + " UNIQUE (" + Tutorial.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, Tutorial.TABLE_NAME, Tutorial.KEY);
    }

    private void createTutorialFrameTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.TutorialFrame.TABLE_NAME + " ("
                        + Tables.TutorialFrame.KEY + " TEXT,"
                        + Tables.TutorialFrame.TUTORIAL_KEY + " TEXT,"
                        + Tables.TutorialFrame.FRAME_TYPE + " TEXT,"
                        + Tables.TutorialFrame.FRAME_TYPE_KEY + " TEXT,"
                        + Tables.TutorialFrame.POSITION + " INTEGER,"
                        + " UNIQUE (" + Tables.TutorialFrame.KEY + ") ON CONFLICT REPLACE)"
        );

        createIndex(db, Tables.TutorialFrame.TABLE_NAME, Tables.TutorialFrame.KEY);
    }

    private void createIndex(SQLiteDatabase db, String tableName, String column) {
        db.execSQL("CREATE INDEX " + tableName + column +
                        " ON " + tableName + " (" + column + ")"
        );
    }

}

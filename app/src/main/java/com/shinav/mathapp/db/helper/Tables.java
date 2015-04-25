package com.shinav.mathapp.db.helper;

public interface Tables {

    public interface Question {
        String TABLE_NAME = "question";
        String KEY = "key";

        String TITLE = "title";
        String VALUE = "value";
        String ANSWER = "answer";
        String EXPLANATION = "explanation";
        String BACKGROUND_IMAGE_URL = "background_image_url";
        String ANNEX_IMAGE_URL = "annex_image_url";
        String PROGRESS_STATE = "progress_state";
    }

    public interface QuestionApproach {
        String TABLE_NAME = "question_approach";
        String KEY = "key";

        String QUESTION_KEY = "question_key";
    }

    public interface QuestionApproachPart {
        String TABLE_NAME = "question_approach_part";
        String KEY = "key";

        String QUESTION_APPROACH_KEY = "question_approach_key";

        String VALUE = "value";
        String POSITION = "position";
    }

    public interface GivenQuestionApproach {
        String TABLE_NAME = "given_question_approach";
        String KEY = "key";

        String QUESTION_APPROACH_KEY = "question_approach_key";

        String ARRANGEMENT = "arrangement";
        String GIVEN_AT = "given_at";
    }

    public interface GivenAnswer {
        String TABLE_NAME = "given_answer";
        String KEY = "key";

        String QUESTION_KEY = "question_key";

        String VALUE = "value";
        String GIVEN_AT = "given_at";
    }

    public interface QuestionExplanation {
        String TABLE_NAME = "question_explanation";
        String KEY = "key";

        String QUESTION_KEY = "question_key";

        String TEXT = "text";
        String IMAGE_URL = "image_url";
        String POSITION = "position";
    }

    public interface Storyboard {
        String TABLE_NAME = "storyboard";
        String KEY = "key";
        
        String TITLE = "title";
    }

    public interface StoryboardFrame {
        String TABLE_NAME = "storyboard_frame";
        String KEY = "key";

        String STORYBOARD_KEY = "storyboard_key";

        String POSITION = "position";
        String FRAME_TYPE = "frame_type";
        String FRAME_TYPE_KEY = "frame_type_key";
    }

    public interface Conversation {
        String TABLE_NAME = "conversation";
        String KEY = "key";
        String TITLE = "title";
        String BACKGROUND_IMAGE_URL = "background_image_url";
    }

    public interface ConversationLine {
        String TABLE_NAME = "conversation_line";
        String KEY = "key";

        String CONVERSATION_KEY = "conversation_key";

        String VALUE = "value";
        String DELAY = "delay";
        String TYPING_DURATION = "typing_duration";
        String POSITION = "position";
        String ALIGNMENT = "alignment";
        String IMAGE_URL = "image_url";
    }

    public interface Tutorial {
        String TABLE_NAME = "tutorial";
        String KEY = "key";

        String TITLE = "title";
    }

    public interface TutorialFrame {
        String TABLE_NAME = "tutorial_frame";
        String KEY = "key";

        String TUTORIAL_KEY = "tutorial_key";

        String POSITION = "position";
        String FRAME_TYPE = "frame_type";
        String FRAME_TYPE_KEY = "frame_type_key";
    }

}

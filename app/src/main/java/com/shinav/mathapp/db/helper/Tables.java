package com.shinav.mathapp.db.helper;

public interface Tables {

    public interface Question {
        String TABLE_NAME = "question";
        String KEY = "key";

        String TITLE = "title";
        String VALUE = "value";
        String ANSWER = "answer";
        String EXPLANATION = "explanation";
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

    public interface Storyboard {
        String TABLE_NAME = "storyboard";
        String KEY = "key";
        
        String PERSPECTIVE = "perspective";
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

    public interface StoryProgress {
        String TABLE_NAME = "story_progress";
        String KEY = "key";
    }

    public interface StoryProgressPart {
        String TABLE_NAME = "story_progress_part";
        String KEY = "key";

        String STORY_PROGRESS_KEY = "story_progress_key";

        String QUESTION_KEY = "question_key";
        String STATE = "state";
        String TITLE = "title";
    }

    public interface Tutorial {
        String TABLE_NAME = "tutorial";
        String KEY = "key";

        String PERSPECTIVE = "perspective";
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

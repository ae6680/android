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

    public interface Approach {
        String TABLE_NAME = "approach";
        String KEY = "key";

        String QUESTION_KEY = "question_key";
    }

    public interface ApproachPart {
        String TABLE_NAME = "approach_part";
        String KEY = "key";

        String APPROACH_KEY = "approach_key";

        String VALUE = "value";
        String POSITION = "position";
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

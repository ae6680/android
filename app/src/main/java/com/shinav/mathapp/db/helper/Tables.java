package com.shinav.mathapp.db.helper;

public interface Tables {

    interface Question {
        String TABLE_NAME = "question";
        String KEY = "key";

        String TITLE = "title";
        String VALUE = "value";
        String ANSWER = "answer";
        String BACKGROUND_IMAGE_URL = "background_image_url";
        String ANNEX_IMAGE_URL = "annex_image_url";
        String PROGRESS_STATE = "progress_state";
    }

    interface QuestionApproach {
        String TABLE_NAME = "question_approach";
        String KEY = "key";

        String QUESTION_KEY = "question_key";
    }

    interface QuestionApproachPart {
        String TABLE_NAME = "question_approach_part";
        String KEY = "key";

        String QUESTION_APPROACH_KEY = "question_approach_key";

        String VALUE = "value";
        String POSITION = "position";
    }

    interface GivenQuestionApproach {
        String TABLE_NAME = "given_question_approach";
        String KEY = "key";

        String QUESTION_APPROACH_KEY = "question_approach_key";

        String ARRANGEMENT = "arrangement";
        String GIVEN_AT = "given_at";
    }

    interface GivenAnswer {
        String TABLE_NAME = "given_answer";
        String KEY = "key";

        String QUESTION_KEY = "question_key";

        String VALUE = "value";
        String GIVEN_AT = "given_at";
    }

    interface QuestionExplanation {
        String TABLE_NAME = "question_explanation";
        String KEY = "key";

        String QUESTION_KEY = "question_key";

        String TEXT = "text";
        String IMAGE_URL = "image_url";
        String POSITION = "position";
    }

    interface Storyboard {
        String TABLE_NAME = "storyboard";
        String KEY = "key";
        
        String TITLE = "title";
    }

    interface StoryboardFrame {
        String TABLE_NAME = "storyboard_frame";
        String KEY = "key";

        String STORYBOARD_KEY = "storyboard_key";

        String POSITION = "position";
        String FRAME_TYPE = "frame_type";
        String FRAME_TYPE_KEY = "frame_type_key";
    }

    interface Cutscene {
        String TABLE_NAME = "cutscene";
        String KEY = "key";
        String TITLE = "title";
        String BACKGROUND_IMAGE_URL = "background_image_url";
    }

    interface CutsceneLine {
        String TABLE_NAME = "cutscene_line";
        String KEY = "key";

        String CUTSCENE_KEY = "cutscene_key";

        String VALUE = "value";
        String DELAY = "delay";
        String TYPING_DURATION = "typing_duration";
        String POSITION = "position";
        String ALIGNMENT = "alignment";
        String IMAGE_URL = "image_url";
        String MAIN_CHARACTER = "main_character";
    }

    interface CutsceneNotice {
        String TABLE_NAME = "cutscene_notice";
        String KEY = "key";

        String CUTSCENE_KEY = "cutscene_key";

        String TEXT = "text";
        String POSITION = "position";
        String ALIGNMENT = "alignment";
        String IMAGE_URL = "image_url";
    }

    interface Tutorial {
        String TABLE_NAME = "tutorial";
        String KEY = "key";

        String TITLE = "title";
    }

    interface TutorialFrame {
        String TABLE_NAME = "tutorial_frame";
        String KEY = "key";

        String TUTORIAL_KEY = "tutorial_key";

        String POSITION = "position";
        String FRAME_TYPE = "frame_type";
        String FRAME_TYPE_KEY = "frame_type_key";
    }

}

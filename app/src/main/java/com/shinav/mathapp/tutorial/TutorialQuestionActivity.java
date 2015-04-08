package com.shinav.mathapp.tutorial;

import android.content.Intent;

import com.shinav.mathapp.event.OnNextQuestionClickedEvent;
import com.shinav.mathapp.question.QuestionActivity;

public class TutorialQuestionActivity extends QuestionActivity {

    @Override public void onNextButtonClicked(OnNextQuestionClickedEvent event) {

        Intent intent = new Intent(this, TutorialManagingService.class);

        intent.setAction(TutorialManagingService.ACTION_NEXT);

        startService(intent);

    }

    @Override public void onBackPressed() {

        Intent intent = new Intent(this, TutorialManagingService.class);

        intent.setAction(TutorialManagingService.ACTION_RESET);

        startService(intent);

        super.onBackPressed();
    }

}

package com.shinav.mathapp.tutorial;

import android.content.Intent;

import com.shinav.mathapp.injection.component.Injector;
import com.shinav.mathapp.questionApproachFeedback.QAFActivity;

import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.FRAME_TYPE_KEY;
import static com.shinav.mathapp.db.pojo.TutorialFrame.APPROACH_FEEDBACK;

public class TutorialQAFActivity extends QAFActivity {

    @Override public void inject() {
        Injector.getActivityComponent(this).inject(this);
    }

    @Override public void next() {
        Intent intent = new Intent(this, TutorialManagingService.class);

        String questionKey = getIntent().getStringExtra(FRAME_TYPE_KEY);

        intent.setAction(TutorialManagingService.ACTION_START_NEXT_FROM);

        intent.putExtra(TutorialManagingService.EXTRA_FRAME_TYPE, APPROACH_FEEDBACK);
        intent.putExtra(TutorialManagingService.EXTRA_FRAME_TYPE_KEY, questionKey);

        startService(intent);
    }
}

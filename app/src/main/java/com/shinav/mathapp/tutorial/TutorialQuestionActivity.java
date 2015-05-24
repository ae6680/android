package com.shinav.mathapp.tutorial;

import android.content.Intent;

import com.shinav.mathapp.event.AnswerFieldClickedEvent;
import com.shinav.mathapp.event.AnswerSubmittedEvent;
import com.shinav.mathapp.event.CalculatorResultAreaClickedEvent;
import com.shinav.mathapp.event.NextQuestionClickedEvent;
import com.shinav.mathapp.event.NumpadOperationClickedEvent;
import com.shinav.mathapp.injection.component.Injector;
import com.shinav.mathapp.question.QuestionActivity;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.FRAME_TYPE_KEY;
import static com.shinav.mathapp.db.pojo.TutorialFrame.QUESTION;

public class TutorialQuestionActivity extends QuestionActivity {

    @Inject Bus bus;

    @Override public void registerBus() {
        bus.register(this);
    }

    @Override public void unregisterBus() {
        bus.unregister(this);
    }

    @Override public void inject() {
        Injector.getActivityComponent(this).inject(TutorialQuestionActivity.this);
    }

    @Override @Subscribe public void onNextButtonClicked(NextQuestionClickedEvent event) {
        Intent intent = new Intent(this, TutorialManagingService.class);

        String questionKey = getIntent().getStringExtra(FRAME_TYPE_KEY);

        intent.setAction(TutorialManagingService.ACTION_START_NEXT_FROM);

        intent.putExtra(TutorialManagingService.EXTRA_FRAME_TYPE, QUESTION);
        intent.putExtra(TutorialManagingService.EXTRA_FRAME_TYPE_KEY, questionKey);

        startService(intent);
    }

    @Subscribe public void OnAnswerSubmittedEvent(AnswerSubmittedEvent event) {
        super.OnAnswerSubmittedEvent(event);
    }

    @Subscribe public void onAnswerFieldClicked(AnswerFieldClickedEvent event) {
        super.onAnswerFieldClicked(event);
    }

    @Subscribe public void onCalculatorResultAreaClicked(CalculatorResultAreaClickedEvent event) {
        super.onCalculatorResultAreaClicked(event);
    }

    @Subscribe public void onCalculatorNumpadClicked(NumpadOperationClickedEvent event) {
        super.onCalculatorNumpadClicked(event);
    }

}

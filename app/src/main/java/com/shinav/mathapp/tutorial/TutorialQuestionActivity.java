package com.shinav.mathapp.tutorial;

import android.content.Intent;

import com.shinav.mathapp.db.dataMapper.StoryProgressMapper;
import com.shinav.mathapp.db.dataMapper.StoryProgressPartMapper;
import com.shinav.mathapp.event.OnAnswerSubmittedEvent;
import com.shinav.mathapp.event.OnCalculatorResultAreaClickedEvent;
import com.shinav.mathapp.event.OnNextQuestionClickedEvent;
import com.shinav.mathapp.event.OnNumpadOperationClickedEvent;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.question.QuestionActivity;
import com.shinav.mathapp.question.event.OnAnswerFieldClickedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

public class TutorialQuestionActivity extends QuestionActivity {

    @Inject Bus bus;
    @Inject StoryProgressMapper storyProgressMapper;
    @Inject StoryProgressPartMapper storyProgressPartMapper;

    @Override public void registerBus() {
        bus.register(this);
    }

    @Override public void unregisterBus() {
        bus.unregister(this);
    }

    @Override public void inject() {
        ComponentFactory.getActivityComponent(this).inject(TutorialQuestionActivity.this);
    }

    @Override public void onNextButtonClicked(OnNextQuestionClickedEvent event) {

        Intent intent = new Intent(this, TutorialManagingService.class);

        intent.setAction(TutorialManagingService.ACTION_NEXT);

        startService(intent);

    }

    @Override public void onBackPressed() {

        Intent intent = new Intent(this, TutorialManagingService.class);

        intent.setAction(TutorialManagingService.ACTION_BACK);

        startService(intent);

        super.onBackPressed();
    }

    @Subscribe public void OnAnswerSubmittedEvent(OnAnswerSubmittedEvent event) {
        super.OnAnswerSubmittedEvent(event);
    }

    @Subscribe public void onAnswerFieldClicked(OnAnswerFieldClickedEvent event) {
        super.onAnswerFieldClicked(event);
    }

    @Subscribe public void onCalculatorResultAreaClicked(OnCalculatorResultAreaClickedEvent event) {
        super.onCalculatorResultAreaClicked(event);
    }

    @Subscribe public void onCalculatorNumpadClicked(OnNumpadOperationClickedEvent event) {
        super.onCalculatorNumpadClicked(event);
    }

}

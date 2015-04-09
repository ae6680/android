package com.shinav.mathapp.tutorial;

import android.content.Intent;

import com.shinav.mathapp.conversation.ConversationActivity;
import com.shinav.mathapp.event.ConversationMessageShownEvent;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

public class TutorialConversationActivity extends ConversationActivity {

    @Inject Bus bus;

    @Override public void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Override public void onSubmitClicked() {

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

    @Override public void inject() {
        ComponentFactory.getActivityComponent(this).inject(TutorialConversationActivity.this);
    }

    @Override @Subscribe public void onConversationMessageShown(ConversationMessageShownEvent event) {
        super.onConversationMessageShown(event);
    }
}

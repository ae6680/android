package com.shinav.mathapp.tutorial;

import android.content.Intent;

import com.shinav.mathapp.conversation.ConversationActivity;

public class TutorialConversationActivity extends ConversationActivity {

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
}

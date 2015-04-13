package com.shinav.mathapp.tutorial;

import android.content.Intent;

import com.shinav.mathapp.approach.ApproachActivity;
import com.shinav.mathapp.injection.component.ComponentFactory;

public class TutorialApproachActivity extends ApproachActivity {

    @Override public void inject() {
        ComponentFactory.getActivityComponent(this).inject(this);
    }

    @Override public void next() {
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

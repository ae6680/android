package com.shinav.mathapp.tutorial;

import android.content.Intent;

import com.shinav.mathapp.cutscene.CutsceneActivity;
import com.shinav.mathapp.event.CutsceneLineTextShownEvent;
import com.shinav.mathapp.injection.component.Injector;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

public class TutorialCutsceneActivity extends CutsceneActivity {

    @Inject Bus bus;

    @Override public void registerBus() {
        bus.register(this);
    }

    @Override public void unregisterBus() {
        bus.unregister(this);
    }

    @Override public void inject() {
        Injector.getActivityComponent(this).inject(TutorialCutsceneActivity.this);
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

    @Override @Subscribe public void onCutsceneTextShown(CutsceneLineTextShownEvent event) {
        super.onCutsceneTextShown(event);
    }
}

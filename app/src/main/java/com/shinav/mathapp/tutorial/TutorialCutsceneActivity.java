package com.shinav.mathapp.tutorial;

import android.content.Intent;

import com.shinav.mathapp.cutscene.CutsceneActivity;
import com.shinav.mathapp.event.CutsceneLineTextShownEvent;
import com.shinav.mathapp.injection.component.Injector;
import com.squareup.otto.Subscribe;

import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.FRAME_TYPE_KEY;
import static com.shinav.mathapp.db.pojo.TutorialFrame.CUTSCENE;

public class TutorialCutsceneActivity extends CutsceneActivity {

    @Override public void registerBus() {
        super.bus.register(this);
    }

    @Override public void unregisterBus() {
        super.bus.unregister(this);
    }

    @Override public void inject() {
        Injector.getActivityComponent(this).inject(TutorialCutsceneActivity.this);
    }

    @Override public void onSubmitClicked() {
        Intent intent = new Intent(this, TutorialManagingService.class);

        String cutsceneKey = getIntent().getStringExtra(FRAME_TYPE_KEY);

        intent.setAction(TutorialManagingService.ACTION_START_NEXT_FROM);

        intent.putExtra(TutorialManagingService.EXTRA_FRAME_TYPE, CUTSCENE);
        intent.putExtra(TutorialManagingService.EXTRA_FRAME_TYPE_KEY, cutsceneKey);

        startService(intent);
    }

    @Override @Subscribe public void onCutsceneTextShown(CutsceneLineTextShownEvent event) {
        super.onCutsceneTextShown(event);
    }
}

package com.shinav.mathapp.hub;

import android.os.Bundle;

import com.shinav.mathapp.R;
import com.shinav.mathapp.injection.ActivityModule;
import com.shinav.mathapp.injection.InjectedActivity;
import com.shinav.mathapp.progress.Storyteller;
import com.shinav.mathapp.sync.FirebaseChildRegisterer;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HubActivity extends InjectedActivity {

    @Inject Storyteller storyTeller;
    @Inject FirebaseChildRegisterer registerer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hub);

        ButterKnife.inject(this);

        registerer.register();
    }

    @Override public ActivityModule getModules() {
        return new ActivityModule(this);
    }

    @OnClick(R.id.story)
    public void onStoryClicked() {
        storyTeller.current();
    }

}

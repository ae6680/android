package com.shinav.mathapp.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.pojo.Tutorial;
import com.shinav.mathapp.db.repository.TutorialRepository;
import com.shinav.mathapp.event.TutorialStartButtonClickedEvent;
import com.shinav.mathapp.injection.component.Injector;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.functions.Action1;

public class TutorialActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar) Toolbar toolbar;

    @Inject Bus bus;
    @Inject TutorialRepository tutorialRepository;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        ButterKnife.inject(this);
        Injector.getActivityComponent(this).inject(this);

        initToolbar();
    }

    @Override public void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    private void initToolbar() {
        toolbar.setTitle(getResources().getString(R.string.choose_character));
        setSupportActionBar(toolbar);
    }

    private void startTutorialManagingService(String tutorialKey, int resourceId) {
        Intent intent = new Intent(this, TutorialManagingService.class);

        intent.setAction(TutorialManagingService.ACTION_START);
        intent.putExtra(TutorialManagingService.EXTRA_TUTORIAL_KEY, tutorialKey);
        intent.putExtra(TutorialManagingService.EXTRA_CHOSEN_CHARACTER, resourceId);

        startService(intent);
    }

    @Subscribe public void onTutorialStartButtonClicked(final TutorialStartButtonClickedEvent event) {

        tutorialRepository.getFirst(new Action1<Tutorial>() {
            @Override public void call(Tutorial tutorial) {
                startTutorialManagingService(
                        tutorial.getKey(),
                        event.getResourceId()
                );
            }
        });
    }

}

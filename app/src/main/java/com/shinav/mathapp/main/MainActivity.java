package com.shinav.mathapp.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.pojo.Storyboard;
import com.shinav.mathapp.db.repository.CutsceneRepository;
import com.shinav.mathapp.db.repository.QuestionRepository;
import com.shinav.mathapp.db.repository.StoryboardFrameRepository;
import com.shinav.mathapp.db.repository.StoryboardRepository;
import com.shinav.mathapp.event.StoryboardFrameListItemClickedEvent;
import com.shinav.mathapp.firebase.FirebaseChildRegisterer;
import com.shinav.mathapp.injection.component.Injector;
import com.shinav.mathapp.main.storyboard.StoryboardFrameListItem;
import com.shinav.mathapp.main.storyboard.StoryboardView;
import com.shinav.mathapp.main.storyboard.func.StoryboardFramesReadyFunc;
import com.shinav.mathapp.storytelling.StorytellingService;
import com.shinav.mathapp.tab.TabsView;
import com.shinav.mathapp.tutorial.TutorialActivity;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.shinav.mathapp.MyApplication.PREF_TUTORIAL_COMPLETED;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.tabs_view) TabsView tabsView;

    @InjectView(R.id.overlay) View overlay;
    @InjectView(R.id.progress) LinearLayout progress;
    @InjectView(R.id.internet_not_found) LinearLayout internetNotFound;

    @Inject Bus bus;
    @Inject FirebaseChildRegisterer registerer;
    @Inject SharedPreferences sharedPreferences;
    @Inject UpdateChecker updateChecker;

    @Inject StoryboardView storyboardView;

    @Inject QuestionRepository questionRepository;
    @Inject CutsceneRepository cutsceneRepository;

    @Inject StoryboardRepository storyboardRepository;
    @Inject StoryboardFrameRepository storyboardFrameRepository;

    private boolean tutorialCompleted;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        Injector.getActivityComponent(this).inject(this);

        initToolbar();
        initTabs();
    }

    @Override public void onStart() {
        super.onStart();
        bus.register(this);
        updateIfNeededAndLoad();
    }

    @Override public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @OnClick(R.id.retry_internet_connection)
    public void onRetryInternetConnectionClicked() {
        updateIfNeededAndLoad();
    }

    private void updateIfNeededAndLoad() {
        internetNotFound.setVisibility(GONE);
        overlay.setVisibility(VISIBLE);
        progress.setVisibility(VISIBLE);

        tutorialCompleted = sharedPreferences.getBoolean(PREF_TUTORIAL_COMPLETED, false);

        if (updateChecker.check()) {
            registerer.register();
            waitAndLoad();

        } else if (!tutorialCompleted) {
            progress.setVisibility(GONE);
            internetNotFound.setVisibility(VISIBLE);
        }  else {
            hideOverlayViews();
            loadStoryboard();
        }
    }

    private void waitAndLoad() {
        Observable.timer(5000, MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override public void call(Long aLong) {
                        hideOverlayViews();

                        if (tutorialCompleted) {
                            loadStoryboard();
                        } else {
                            showTutorial();
                        }
                    }
                });
    }

    private void hideOverlayViews() {
        overlay.setVisibility(GONE);
        progress.setVisibility(GONE);
        internetNotFound.setVisibility(GONE);
    }

    private void loadStoryboard() {
        storyboardRepository.getFirst(new Action1<Storyboard>() {
            @Override public void call(Storyboard storyboard) {
                loadStoryboardFrames(storyboard);
                startStorytellingService(storyboard.getKey());
            }
        });
    }

    private void loadStoryboardFrames(final Storyboard storyboard) {
        storyboardFrameRepository.findAllByParent(
                storyboard.getKey(), new StoryboardFramesReadyFunc(
                        cutsceneRepository,
                        questionRepository,
                        new Action1<List<StoryboardFrameListItem>>() {
                            @Override
                            public void call(List<StoryboardFrameListItem> listItems) {
                                storyboardView.setListItems(listItems);
                            }
                        }
                ));
    }

    private void initToolbar() {
        toolbar.setTitle(R.string.main_toolbar_title);
        setSupportActionBar(toolbar);
    }

    private void initTabs() {
        tabsView.addTab(getResources().getString(R.string.main_tab_1), storyboardView);
    }

    private void showTutorial() {
        Intent intent = new Intent(this, TutorialActivity.class);
        startActivity(intent);
        finish();
    }

    private void startStorytellingService(String storyboardKey) {
        Intent intent = new Intent(this, StorytellingService.class);

        intent.setAction(StorytellingService.ACTION_START);
        intent.putExtra(StorytellingService.EXTRA_STORYBOARD_KEY, storyboardKey);

        startService(intent);
    }

    @Subscribe public void onStoryboardFrameListItemClicked(StoryboardFrameListItemClickedEvent event) {
        Intent intent = new Intent(this, StorytellingService.class);

        intent.setAction(StorytellingService.ACTION_START_FROM);
        intent.putExtra(StorytellingService.EXTRA_FRAME_TYPE_KEY, event.getFrameTypeKey());

        startService(intent);
    }

}

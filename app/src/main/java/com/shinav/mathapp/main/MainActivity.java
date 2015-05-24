package com.shinav.mathapp.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.pojo.Cutscene;
import com.shinav.mathapp.db.pojo.Question;
import com.shinav.mathapp.db.pojo.Storyboard;
import com.shinav.mathapp.db.pojo.StoryboardFrame;
import com.shinav.mathapp.db.repository.CutsceneRepository;
import com.shinav.mathapp.db.repository.QuestionRepository;
import com.shinav.mathapp.db.repository.StoryboardFrameRepository;
import com.shinav.mathapp.db.repository.StoryboardRepository;
import com.shinav.mathapp.event.StoryboardFrameListItemClickedEvent;
import com.shinav.mathapp.firebase.FirebaseChildRegisterer;
import com.shinav.mathapp.injection.component.Injector;
import com.shinav.mathapp.main.storyboard.StoryboardFrameListItem;
import com.shinav.mathapp.main.storyboard.StoryboardView;
import com.shinav.mathapp.storytelling.StorytellingService;
import com.shinav.mathapp.tab.TabsView;
import com.shinav.mathapp.tutorial.TutorialActivity;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.shinav.mathapp.MyApplication.PREF_DATA_UPDATED_AT;
import static com.shinav.mathapp.MyApplication.PREF_TUTORIAL_COMPLETED;
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.STATE_CLOSED;
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.STATE_OPENED;
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.TYPE_CUTSCENE;
import static com.shinav.mathapp.main.storyboard.StoryboardFrameListItem.TYPE_QUESTION;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.tabs_view) TabsView tabsView;

    @InjectView(R.id.overlay) View overlay;
    @InjectView(R.id.progress) ProgressBar progressBar;
    @InjectView(R.id.internet_not_found) LinearLayout internetNotFound;

    @Inject Bus bus;
    @Inject FirebaseChildRegisterer registerer;
    @Inject SharedPreferences sharedPreferences;

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
        updateAndLoad();
    }

    @OnClick(R.id.retry_internet_connection)
    public void onRetryInternetConnectionClicked() {
        updateAndLoad();
    }

    @Override public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    private void updateAndLoad() {
        internetNotFound.setVisibility(GONE);
        overlay.setVisibility(VISIBLE);
        progressBar.setVisibility(VISIBLE);

        tutorialCompleted = sharedPreferences.getBoolean(PREF_TUTORIAL_COMPLETED, false);

        long dayOfLatestUpdate = MILLISECONDS.toDays(sharedPreferences.getLong(PREF_DATA_UPDATED_AT, 0));
        long today = MILLISECONDS.toDays(System.currentTimeMillis());

        if ((!tutorialCompleted || today > dayOfLatestUpdate) && isOnline()) {
            registerer.register();
            updatePrefDataUpdatedAt();
            waitAndLoad();

        } else if (!tutorialCompleted) {
            progressBar.setVisibility(GONE);
            internetNotFound.setVisibility(VISIBLE);
        }  else {
            hideOverlayViews();
            loadStoryboard();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void updatePrefDataUpdatedAt() {
        sharedPreferences.edit().putLong(
                PREF_DATA_UPDATED_AT,
                System.currentTimeMillis()
        ).apply();
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
        progressBar.setVisibility(GONE);
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

        final Observable<List<StoryboardFrame>> framesObservable =
                storyboardFrameRepository.getByStoryboardKey(storyboard.getKey()).first();

        framesObservable.subscribe(new Action1<List<StoryboardFrame>>() {
            @Override public void call(List<StoryboardFrame> storyboardFrames) {

                List<String> questionKeys = new ArrayList<>();
                List<String> cutsceneKeys = new ArrayList<>();

                for (StoryboardFrame storyboardFrame : storyboardFrames) {
                    if (storyboardFrame.isQuestion()) {
                        questionKeys.add(storyboardFrame.getFrameTypeKey());

                    } else if (storyboardFrame.isCutscene()) {
                        cutsceneKeys.add(storyboardFrame.getFrameTypeKey());
                    }
                }

                String questionKeysString = TextUtils.join("','", questionKeys);
                String cutsceneKeysString = TextUtils.join("','", cutsceneKeys);

                Observable<List<Question>> questionObservable =
                        questionRepository.getCollection(questionKeysString)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread()).first();

                Observable<List<StoryboardFrameListItem>> questionFramesObservable =
                        questionObservable.map(new Func1<List<Question>, List<StoryboardFrameListItem>>() {
                            @Override
                            public List<StoryboardFrameListItem> call(List<Question> questions) {

                                List<StoryboardFrameListItem> listItems = new ArrayList<>();

                                for (final Question question : questions) {

                                    listItems.add(new StoryboardFrameListItem() {

                                                      @Override public String getKey() {
                                                          return question.getKey();
                                                      }

                                                      @Override public String getType() {
                                                          return StoryboardFrameListItem.TYPE_QUESTION;
                                                      }

                                                      @Override public String getTitle() {
                                                          return question.getTitle();
                                                      }

                                                      @Override public int getState() {
                                                          return question.getProgressState();
                                                      }

                                                      @Override public void setState(int state) {
                                                          question.setProgressState(state);
                                                      }

                                                      @Override public String getBackgroundImage() {
                                                          return question.getBackgroundImageUrl();
                                                      }
                                                  }
                                    );
                                }

                                return listItems;
                            }
                        });

                Observable<List<Cutscene>> cutsceneObservable =
                        cutsceneRepository.getCollection(cutsceneKeysString)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread()).first();

                Observable<List<StoryboardFrameListItem>> cutsceneFramesObservable =

                        cutsceneObservable.map(new Func1<List<Cutscene>, List<StoryboardFrameListItem>>() {
                            @Override
                            public List<StoryboardFrameListItem> call(List<Cutscene> cutscenes) {

                                List<StoryboardFrameListItem> listItems = new ArrayList<>();

                                for (final Cutscene cutscene : cutscenes) {

                                    listItems.add(new StoryboardFrameListItem() {

                                                      @Override public String getKey() {
                                                          return cutscene.getKey();
                                                      }

                                                      @Override public String getType() {
                                                          return StoryboardFrameListItem.TYPE_CUTSCENE;
                                                      }

                                                      @Override public String getTitle() {
                                                          return cutscene.getTitle();
                                                      }

                                                      @Override public int getState() {
                                                          return cutscene.getState();
                                                      }

                                                      @Override public void setState(int state) {
                                                          cutscene.setState(state);
                                                      }

                                                      @Override public String getBackgroundImage() {
                                                          return cutscene.getBackgroundImageUrl();
                                                      }
                                                  }
                                    );
                                }

                                return listItems;
                            }
                        });

                Observable.combineLatest(
                        framesObservable,
                        questionFramesObservable,
                        cutsceneFramesObservable,
                        new StoryboardFramesReadyFunc()
                ).first().subscribe(new Action1<List<StoryboardFrameListItem>>() {
                    @Override
                    public void call(List<StoryboardFrameListItem> listItems) {
                        setupStoryFrameStates(listItems);
                        storyboardView.setListItems(listItems);
                    }
                });

            }
        });
    }

    private void setupStoryFrameStates(List<StoryboardFrameListItem> listItems) {
        for (StoryboardFrameListItem listItem : listItems) {

            // Open up all cutscenes until current open
            // question is found.
            if (listItem.getType().equals(TYPE_CUTSCENE)) {
                listItem.setState(STATE_OPENED);
            }

            if (listItem.getType().equals(TYPE_QUESTION)) {
                // Open up first question if closed after tutorial.
                if (listItem.getState() == STATE_CLOSED) {
                    listItem.setState(STATE_OPENED);
                }

                // Currently open question is found, break.
                if (listItem.getState() == STATE_OPENED) {
                    break;
                }
            }
        }
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

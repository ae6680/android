package com.shinav.mathapp.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.shinav.mathapp.MyApplication.PREF_TUTORIAL_COMPLETED;

public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.tabs_view) TabsView tabsView;
    @InjectView(R.id.progress) ProgressBar progressBar;

    @Inject Bus bus;
    @Inject FirebaseChildRegisterer registerer;
    @Inject SharedPreferences sharedPreferences;

    @Inject StoryboardView storyboardView;

    @Inject QuestionRepository questionRepository;
    @Inject CutsceneRepository cutsceneRepository;

    @Inject StoryboardRepository storyboardRepository;
    @Inject StoryboardFrameRepository storyboardFrameRepository;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        Injector.getActivityComponent(this).inject(this);

        registerer.register();

        initToolbar();
        initTabs();
    }

    @Override public void onStart() {
        super.onStart();
        bus.register(this);
        loadStoryboardFrames();
    }

    @Override public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    private void loadStoryboardFrames() {

        boolean tutorialCompleted = sharedPreferences.getBoolean(PREF_TUTORIAL_COMPLETED, false);

        if (tutorialCompleted) {
            loadStoryboard();
        } else {
            progressBar.setVisibility(VISIBLE);

            // Wait 5 seconds to load the data the first time.
            Observable.timer(5000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override public void call(Long aLong) {
                            progressBar.setVisibility(GONE);
                            showTutorial();
                        }
                    });
        }

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
                storyboardFrameRepository.getByStoryboardKey(storyboard.getKey());

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
                                .observeOn(AndroidSchedulers.mainThread());

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
                                .observeOn(AndroidSchedulers.mainThread());

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
                                                          return STATE_CLOSED;
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
                        storyboardView.setListItems(listItems);
                    }
                });

            }
        });
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

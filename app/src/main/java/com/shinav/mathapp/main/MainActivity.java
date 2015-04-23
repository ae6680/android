package com.shinav.mathapp.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ProgressBar;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.R;
import com.shinav.mathapp.db.dataMapper.StoryProgressMapper;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.Question;
import com.shinav.mathapp.db.pojo.Storyboard;
import com.shinav.mathapp.db.pojo.StoryboardFrame;
import com.shinav.mathapp.db.repository.QuestionRepository;
import com.shinav.mathapp.db.repository.StoryboardFrameRepository;
import com.shinav.mathapp.db.repository.StoryboardRepository;
import com.shinav.mathapp.firebase.FirebaseChildRegisterer;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.main.storyboard.StoryboardFrameListItem;
import com.shinav.mathapp.main.storyboard.StoryboardFrameListItemClicked;
import com.shinav.mathapp.main.storyboard.StoryboardView;
import com.shinav.mathapp.question.QuestionActivity;
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

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.tabs_view) TabsView tabsView;
    @InjectView(R.id.progress) ProgressBar progressBar;

    @Inject Bus bus;
    @Inject FirebaseChildRegisterer registerer;
    @Inject SharedPreferences sharedPreferences;

    @Inject StoryProgressMapper storyProgressMapper;

    @Inject StoryboardView storyboardView;

    @Inject QuestionRepository questionRepository;

    @Inject StoryboardRepository storyboardRepository;
    @Inject StoryboardFrameRepository storyboardFrameRepository;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        ComponentFactory.getActivityComponent(this).inject(this);

        registerer.register();

        initToolbar();
        initTabs();
    }

    @Override public void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

//   Temporary to reset tutorial
//    @Override protected void onDestroy() {
//        sharedPreferences.edit().clear().apply();
//        super.onDestroy();
//    }

    @Override protected void onResume() {
        super.onResume();
        loadStoryboardFrames();
    }

    private void loadStoryboardFrames() {

        int characterResId = sharedPreferences.getInt(MyApplication.PREF_CHOSEN_CHARACTER, 0);

        if (characterResId == 0) {

            progressBar.setVisibility(VISIBLE);

            toolbar.setTitle(getResources().getString(R.string.choose_character));

            // Wait 5 seconds to load the data the first time.
            Observable.timer(0, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override public void call(Long aLong) {
                            progressBar.setVisibility(GONE);
                            showTutorial();
                        }
                    });

        } else {
            loadStoryboard();
//            startStorytellingService();
        }

    }

    private void loadStoryboard() {
        storyboardRepository.getFirst(new Action1<Storyboard>() {
            @Override public void call(Storyboard storyboard) {
                loadStoryboardFrames(storyboard);
            }
        });
    }

    private void loadStoryboardFrames(Storyboard storyboard) {

        Observable<List<StoryboardFrame>> observable =
                storyboardFrameRepository.getQuestionFrames(storyboard.getKey()).first();

        observable.subscribe(new Action1<List<StoryboardFrame>>() {
            @Override public void call(List<StoryboardFrame> storyboardFrames) {

                List<String> questionKeys = new ArrayList<>();
                for (StoryboardFrame storyboardFrame : storyboardFrames) {
                    if (storyboardFrame.isQuestion()) {
                        questionKeys.add(storyboardFrame.getFrameTypeKey());
                    }
                }

                String questionKeysString = TextUtils.join("','", questionKeys);

                questionRepository.getCollection(questionKeysString, new Action1<List<Question>>() {
                    @Override public void call(List<Question> questions) {

                        List<StoryboardFrameListItem> listItems = new ArrayList<>();

//                        for (final Question question : questions) {
                        for (int i = 0; i < 50; i++) {
                            final Question question = questions.get(0);

                            listItems.add(new StoryboardFrameListItem() {

                                        @Override public String getKey() {
                                            return question.getKey();
                                        }

                                        @Override public String getTitle() {
                                            return question.getTitle();
                                        }

                                        @Override public int getState() {
                                            return -1;
                                        }

                                        @Override public String getBackgroundImage() {
                                            return question.getBackgroundImageUrl();
                                        }
                                    }
                            );
                        }

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

    @Subscribe public void onMakeQuestionButtonClicked(StoryboardFrameListItemClicked event) {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra(Tables.StoryboardFrame.FRAME_TYPE_KEY, event.getKey());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_left_from_outside, R.anim.slide_left_to_outside);
    }

}

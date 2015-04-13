package com.shinav.mathapp.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.R;
import com.shinav.mathapp.db.dataMapper.StoryProgressMapper;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.Storyboard;
import com.shinav.mathapp.db.pojo.StoryboardFrame;
import com.shinav.mathapp.db.repository.StoryProgressPartRepository;
import com.shinav.mathapp.db.repository.StoryProgressRepository;
import com.shinav.mathapp.db.repository.StoryboardFrameRepository;
import com.shinav.mathapp.db.repository.StoryboardRepository;
import com.shinav.mathapp.event.MakeQuestionButtonClicked;
import com.shinav.mathapp.event.TutorialStartButtonClicked;
import com.shinav.mathapp.firebase.FirebaseChildRegisterer;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.main.practice.PracticeOverviewView;
import com.shinav.mathapp.main.storyProgress.StoryboardView;
import com.shinav.mathapp.question.QuestionActivity;
import com.shinav.mathapp.tab.TabsView;
import com.shinav.mathapp.tutorial.TutorialManagingService;
import com.shinav.mathapp.tutorial.TutorialView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

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
    @InjectView(R.id.tutorial_view) TutorialView tutorialView;
    @InjectView(R.id.storyboard_progress) ProgressBar progressBar;
    @InjectView(R.id.background_view) ImageView backgroundView;

    @Inject Bus bus;
    @Inject FirebaseChildRegisterer registerer;
    @Inject SharedPreferences sharedPreferences;

    @Inject StoryProgressMapper storyProgressMapper;

    @Inject StoryboardView storyboardView;
    @Inject PracticeOverviewView practiceOverviewView;

    @Inject StoryProgressRepository storyProgressRepository;
    @Inject StoryProgressPartRepository storyProgressPartRepository;

    @Inject StoryboardRepository storyboardRepository;
    @Inject StoryboardFrameRepository storyboardFrameRepository;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        ComponentFactory.getActivityComponent(this).inject(this);

        loadBackground("http://i.imgur.com/JfDNNOy.png");

        initToolbar();
        initTabs();

        registerer.register();
    }

    @Override public void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Override protected void onResume() {
        super.onResume();
        loadStoryboardFrames();
    }

    private void loadBackground(String imageUrl) {
        Picasso.with(this)
                .load(imageUrl)
                .centerCrop()
                .fit()
                .into(backgroundView);
    }

    private void loadStoryboardFrames() {

        String perspective = sharedPreferences.getString(MyApplication.PREF_PERSPECTIVE, null);

        if (TextUtils.isEmpty(perspective)) {

            progressBar.setVisibility(VISIBLE);

            // Wait 5 seconds to load the data the first time.
            Observable.timer(5000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override public void call(Long aLong) {
                            progressBar.setVisibility(GONE);
                            showTutorialLayout();
                        }
                    });

        } else {
            hideTutorialLayout();
            loadStoryboard(perspective);
        }

    }

    private void loadStoryboard(String perspective) {
        Observable<Storyboard> storyboardObservable =
                storyboardRepository.getByPerspective(perspective);

        storyboardObservable.subscribe(new Action1<Storyboard>() {
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
                storyboardView.setStoryboardFrames(storyboardFrames);
            }
        });
    }

    private void initToolbar() {
        toolbar.setTitle(R.string.main_toolbar_title);
        setSupportActionBar(toolbar);
    }

    private void initTabs() {
        tabsView.addTab(getResources().getString(R.string.main_tab_1), storyboardView);
//        tabsView.addTab(getResources().getString(R.string.main_tab_2), practiceOverviewView);
    }

    private void showTutorialLayout() {
        tutorialView.setVisibility(VISIBLE);
        tabsView.setVisibility(GONE);
    }

    private void hideTutorialLayout() {
        tutorialView.setVisibility(GONE);
        tabsView.setVisibility(VISIBLE);
    }

    private void savePerspectiveInSharedPreferences(String perspective) {
        sharedPreferences.edit().putString(MyApplication.PREF_PERSPECTIVE, perspective).apply();
    }

    private void startTutorialManagingService(String perspective) {
        Intent intent = new Intent(this, TutorialManagingService.class);

        intent.setAction(TutorialManagingService.ACTION_START);
        intent.putExtra(TutorialManagingService.EXTRA_PERSPECTIVE, perspective);

        startService(intent);
    }

    @Subscribe public void onMakeQuestionButtonClicked(MakeQuestionButtonClicked event) {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra(Tables.StoryboardFrame.FRAME_TYPE_KEY, event.getQuestionFirebaseKey());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_left_from_outside, R.anim.slide_left_to_outside);
    }

    @Subscribe public void onTutorialStartButtonClicked(TutorialStartButtonClicked event) {
        savePerspectiveInSharedPreferences(event.getPerspective());
        startTutorialManagingService(event.getPerspective());
    }

}

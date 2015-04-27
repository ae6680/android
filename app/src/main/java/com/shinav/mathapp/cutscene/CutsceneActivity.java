package com.shinav.mathapp.cutscene;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.dataMapper.CutsceneLineMapper;
import com.shinav.mathapp.db.dataMapper.CutsceneMapper;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.Cutscene;
import com.shinav.mathapp.db.pojo.CutsceneLine;
import com.shinav.mathapp.db.repository.CutsceneLineRepository;
import com.shinav.mathapp.db.repository.CutsceneRepository;
import com.shinav.mathapp.event.CutsceneLineTextShownEvent;
import com.shinav.mathapp.injection.component.Injector;
import com.shinav.mathapp.storytelling.StorytellingService;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class CutsceneActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.background_view) ImageView backgroundView;
    @InjectView(R.id.cutscene_container) LinearLayout cutsceneContainer;
    @InjectView(R.id.cutscene_scroll_view) ScrollView cutsceneScrollView;

    @Inject Bus bus;
    @Inject CutsceneLineMapper cutsceneLineMapper;
    @Inject CutsceneMapper cutsceneMapper;

    @Inject CutsceneRepository cutsceneRepository;
    @Inject CutsceneLineRepository cutsceneLineRepository;

    private List<CutsceneLine> cutsceneLines;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutscene);

        ButterKnife.inject(this);
        inject();

        String cutsceneKey = getIntent().getStringExtra(Tables.StoryboardFrame.FRAME_TYPE_KEY);

        loadTitle(cutsceneKey);
        startCutscene(cutsceneKey);
    }

    @Override public void onStart() {
        super.onStart();
        registerBus();
    }

    @Override public void onStop() {
        super.onStop();
        unregisterBus();
    }

    public void inject() {
        Injector.getActivityComponent(this).inject(this);
    }

    public void registerBus() {
        bus.register(this);
    }

    public void unregisterBus() {
        bus.unregister(this);
    }

    private void startCutscene(String cutsceneKey) {
        cutsceneLineRepository.getByCutsceneKey(cutsceneKey).first().subscribe(new Action1<List<CutsceneLine>>() {
            @Override public void call(List<CutsceneLine> cutsceneLines) {

                if (!cutsceneLines.isEmpty()) {
                    CutsceneActivity.this.cutsceneLines = cutsceneLines;
                    startCutsceneLines(cutsceneLines.get(0));
                }

            }
        });
    }

    private void loadTitle(String cutsceneKey) {
        cutsceneRepository.getByKey(cutsceneKey).first().subscribe(new Action1<Cutscene>() {
            @Override public void call(Cutscene cutscene) {
                initToolbar(cutscene);
                loadBackground(cutscene.getBackgroundImageUrl());
            }
        });
    }

    private void initToolbar(Cutscene cutscene) {
        toolbar.setTitle(cutscene.getTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadBackground(String imageUrl) {
        Picasso.with(this)
                .load(imageUrl)
                .centerCrop()
                .fit()
                .into(backgroundView);
        backgroundView.setImageAlpha(50);
    }

    private void startCutsceneLines(final CutsceneLine cutsceneLine) {

        final CutsceneLineView view = new CutsceneLineView(
                CutsceneActivity.this,
                cutsceneLine
        );

        cutsceneContainer.addView(view);
        cutsceneScrollView.post(new Runnable() {
            @Override
            public void run() {
                cutsceneScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        Observable<Long> delayedTimer = Observable.timer(
                cutsceneLine.getTypingDuration(),
                TimeUnit.MILLISECONDS
        );

        delayedTimer
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {

                    @Override public void call(Long aLong) {
                        view.startTyping();
                    }

                });
    }

    @Subscribe public void onCutsceneTextShown(CutsceneLineTextShownEvent event) {
        int nextPos = event.getPosition() + 1;

        if (nextPos < cutsceneLines.size()) {
            startCutsceneLines(cutsceneLines.get(nextPos));
        }
    }

    @OnClick(R.id.next_question_button)
    public void onSubmitClicked() {
        Intent intent = new Intent(this, StorytellingService.class);

        String cutsceneKey =
                getIntent().getStringExtra(Tables.StoryboardFrame.FRAME_TYPE_KEY);

        intent.setAction(StorytellingService.ACTION_START_NEXT_FROM);
        intent.putExtra(StorytellingService.EXTRA_FRAME_TYPE_KEY, cutsceneKey);

        startService(intent);
    }

}

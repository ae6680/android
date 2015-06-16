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
import com.shinav.mathapp.db.dataMapper.CutsceneLineDataMapper;
import com.shinav.mathapp.db.dataMapper.CutsceneDataMapper;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.Cutscene;
import com.shinav.mathapp.db.pojo.CutsceneLine;
import com.shinav.mathapp.db.pojo.CutsceneNotice;
import com.shinav.mathapp.db.repository.CutsceneLineRepository;
import com.shinav.mathapp.db.repository.CutsceneNoticeRepository;
import com.shinav.mathapp.db.repository.CutsceneRepository;
import com.shinav.mathapp.event.CutsceneLineTextShownEvent;
import com.shinav.mathapp.injection.component.Injector;
import com.shinav.mathapp.storytelling.StorytellingService;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class CutsceneActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.background_view) ImageView backgroundView;
    @InjectView(R.id.cutscene_container) LinearLayout cutsceneContainer;
    @InjectView(R.id.cutscene_scroll_view) ScrollView cutsceneScrollView;

    @Inject CutsceneLineDataMapper cutsceneLineDataMapper;
    @Inject CutsceneDataMapper cutsceneDataMapper;

    @Inject CutsceneRepository cutsceneRepository;
    @Inject CutsceneLineRepository cutsceneLineRepository;
    @Inject CutsceneNoticeRepository cutsceneNoticeRepository;

    protected Bus bus;
    private String cutsceneKey;
    private List<CutsceneLine> cutsceneLines = Collections.emptyList();
    private List<CutsceneNotice> aboveNotices = new ArrayList<>();
    private List<CutsceneNotice> underNotices = new ArrayList<>();

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutscene);

        ButterKnife.inject(this);
        inject();

        cutsceneKey = getIntent().getStringExtra(Tables.StoryboardFrame.FRAME_TYPE_KEY);

        loadCutscene(cutsceneKey);
    }

    @Override public void onResume() {
        super.onStart();

        bus = new Bus(ThreadEnforcer.ANY);
        registerBus();

        clearCutscene();
        startCutscene(cutsceneKey);
    }

    @Override public void onPause() {
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

    private void clearCutscene() {
        aboveNotices.clear();
        underNotices.clear();

        if (cutsceneContainer.getChildCount() > 0) {
            cutsceneContainer.removeAllViews();
        }
    }

    private void startCutscene(String cutsceneKey) {
        Observable<List<CutsceneLine>> lineObservable =
                cutsceneLineRepository.findAllByParent(cutsceneKey)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .first();

        Observable<List<CutsceneNotice>> noticesObservable =
                cutsceneNoticeRepository.findAllByParent(cutsceneKey)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .first();

        Observable.combineLatest(
                lineObservable,
                noticesObservable,
                new SceneComponentsReady()
        ).first().subscribe(Actions.empty());
    }

    private void loadCutscene(String cutsceneKey) {
        cutsceneRepository.find(cutsceneKey, new Action1<Object>() {
            @Override public void call(Object o) {
                Cutscene cutscene = (Cutscene) o;
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

    private void startCutsceneLine(final CutsceneLine cutsceneLine) {

        final CutsceneLineView view = new CutsceneLineView(
                CutsceneActivity.this,
                cutsceneLine,
                bus
        );

        cutsceneContainer.addView(view);
        cutsceneScrollView.post(new Runnable() {
            @Override
            public void run() {
                cutsceneScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        Observable.timer(
                cutsceneLine.getTypingDuration(),
                TimeUnit.MILLISECONDS
        )
                .subscribeOn(Schedulers.io())
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
            startCutsceneLine(cutsceneLines.get(nextPos));
        } else {
            for (CutsceneNotice notice : underNotices) {
                addCutsceneNoticeView(notice);
            }
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

    private void addCutsceneNoticeView(CutsceneNotice notice) {
        CutsceneNoticeView noticeView = new CutsceneNoticeView(this);
        noticeView.setText(notice.getText());
        noticeView.setImage(notice.getImageUrl());

        cutsceneContainer.addView(noticeView);
    }

    private class SceneComponentsReady implements
            Func2<List<CutsceneLine>, List<CutsceneNotice>, Void> {

        @Override
        public Void call(List<CutsceneLine> cutsceneLines, List<CutsceneNotice> cutsceneNotices) {

            for (CutsceneNotice notice : cutsceneNotices) {
                if (notice.isAlignedAbove()) {
                    aboveNotices.add(notice);
                } else if (notice.isAlignedUnder()) {
                    underNotices.add(notice);
                }
            }

            for (CutsceneNotice notice : aboveNotices) {
                addCutsceneNoticeView(notice);
            }

            if (!cutsceneLines.isEmpty()) {
                CutsceneActivity.this.cutsceneLines = cutsceneLines;
                startCutsceneLine(cutsceneLines.get(0));
            }

            return null;
        }

    }

}

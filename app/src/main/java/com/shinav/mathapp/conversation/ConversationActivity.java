package com.shinav.mathapp.conversation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.dataMapper.ConversationLineMapper;
import com.shinav.mathapp.db.dataMapper.ConversationMapper;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.Conversation;
import com.shinav.mathapp.db.pojo.ConversationLine;
import com.shinav.mathapp.db.repository.ConversationLineRepository;
import com.shinav.mathapp.db.repository.ConversationRepository;
import com.shinav.mathapp.event.ConversationMessageShownEvent;
import com.shinav.mathapp.injection.component.ComponentFactory;
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

public class ConversationActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.background_view) ImageView backgroundView;
    @InjectView(R.id.conversation_container) LinearLayout conversationContainer;
    @InjectView(R.id.conversation_scroll_view) ScrollView conversationScrollView;

    @Inject Bus bus;
    @Inject ConversationLineMapper conversationLineMapper;
    @Inject ConversationMapper conversationMapper;

    @Inject ConversationRepository conversationRepository;
    @Inject ConversationLineRepository conversationLineRepository;

    private List<ConversationLine> conversationLines;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        ButterKnife.inject(this);
        inject();

        String conversationKey = getIntent().getStringExtra(Tables.StoryboardFrame.FRAME_TYPE_KEY);

        loadTitle(conversationKey);
        startConversation(conversationKey);
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
        ComponentFactory.getActivityComponent(this).inject(this);
    }

    public void registerBus() {
        bus.register(this);
    }

    public void unregisterBus() {
        bus.unregister(this);
    }

    private void startConversation(String conversationKey) {
        conversationLineRepository.getByConversationKey(conversationKey).first().subscribe(new Action1<List<ConversationLine>>() {
            @Override public void call(List<ConversationLine> conversationLines) {

                if (!conversationLines.isEmpty()) {
                    ConversationActivity.this.conversationLines = conversationLines;
                    startConversationPart(conversationLines.get(0));
                }

            }
        });
    }

    private void loadTitle(String conversationKey) {
        conversationRepository.getByKey(conversationKey).first().subscribe(new Action1<Conversation>() {
            @Override public void call(Conversation conversation) {
                initToolbar(conversation);
                loadBackground(conversation.getBackgroundImageUrl());
            }
        });
    }

    private void initToolbar(Conversation conversation) {
        toolbar.setTitle(conversation.getTitle());
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

    private void startConversationPart(final ConversationLine conversationLine) {

        final ConversationLineView view = new ConversationLineView(
                ConversationActivity.this,
                conversationLine
        );

        conversationContainer.addView(view);
        conversationScrollView.post(new Runnable() {
            @Override
            public void run() {
                conversationScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        Observable<Long> delayedTimer = Observable.timer(
                conversationLine.getTypingDuration(),
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

    @Subscribe public void onConversationMessageShown(ConversationMessageShownEvent event) {
        int nextPos = event.getPosition() + 1;

        if (nextPos < conversationLines.size()) {
            startConversationPart(conversationLines.get(nextPos));
        }
    }

    @OnClick(R.id.next_question_button)
    public void onSubmitClicked() {
        Intent intent = new Intent(this, StorytellingService.class);

        String conversationKey =
                getIntent().getStringExtra(Tables.StoryboardFrame.FRAME_TYPE_KEY);

        intent.setAction(StorytellingService.ACTION_NEXT_FROM);
        intent.putExtra(StorytellingService.EXTRA_FRAME_TYPE_KEY, conversationKey);

        startService(intent);
    }

}

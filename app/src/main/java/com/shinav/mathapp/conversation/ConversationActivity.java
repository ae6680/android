package com.shinav.mathapp.conversation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

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

public class ConversationActivity extends Activity {

    @InjectView(R.id.conversation_container) LinearLayout conversationContainer;
    @InjectView(R.id.conversation_title) TextView conversationTitle;

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

    public void inject() {
        ComponentFactory.getActivityComponent(this).inject(this);
    }

    private void startConversation(String conversationKey) {
        conversationLineRepository.getByConversationKey(conversationKey).first().subscribe(new Action1<List<ConversationLine>>() {
            @Override public void call(List<ConversationLine> conversationLines) {

                if (!conversationLines.isEmpty()) {
                    Collections.sort(conversationLines);
                    ConversationActivity.this.conversationLines = conversationLines;
                    startConversationPart(conversationLines.get(0));
                }

            }
        });
    }

    private void loadTitle(String conversationKey) {
        conversationRepository.getByKey(conversationKey).first().subscribe(new Action1<Conversation>() {
            @Override public void call(Conversation conversation) {
                conversationTitle.setText(conversation.getTitle());
            }
        });
    }

    @Override public void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    private void startConversationPart(final ConversationLine conversationLine) {

        final ConversationLineView view = new ConversationLineView(
                ConversationActivity.this,
                conversationLine
        );

        conversationContainer.addView(view);

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
//        storyTeller.next();
    }

}

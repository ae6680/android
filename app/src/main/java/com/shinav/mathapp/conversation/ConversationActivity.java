package com.shinav.mathapp.conversation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.mapper.ConversationMapper;
import com.shinav.mathapp.db.mapper.ConversationPartMapper;
import com.shinav.mathapp.db.pojo.Conversation;
import com.shinav.mathapp.db.pojo.ConversationPart;
import com.shinav.mathapp.db.repository.ConversationPartRepository;
import com.shinav.mathapp.db.repository.ConversationRepository;
import com.shinav.mathapp.event.ConversationMessageShown;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.progress.Storyteller;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

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

    //    @Inject Storyteller storyTeller;
    @Inject Bus bus;
    @Inject ConversationPartMapper conversationPartMapper;
    @Inject ConversationMapper conversationMapper;

    @Inject ConversationRepository conversationRepository;
    @Inject ConversationPartRepository conversationPartRepository;

    private List<ConversationPart> conversationParts;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        ButterKnife.inject(this);
        ComponentFactory.getActivityComponent(this).inject(this);
    }

    @Override protected void onResume() {
        super.onResume();

        String conversationKey = getIntent().getStringExtra(Storyteller.TYPE_KEY);

        conversationRepository.getByKey(conversationKey).first().subscribe(new Action1<Conversation>() {
            @Override public void call(Conversation conversation) {
                conversationTitle.setText(conversation.getTitle());
            }
        });

        conversationPartRepository.getByConversationKey(conversationKey).first().subscribe(new Action1<List<ConversationPart>>() {
            @Override public void call(List<ConversationPart> conversationParts) {

                if (!conversationParts.isEmpty()) {
                    ConversationActivity.this.conversationParts = conversationParts;
                    startConversationPart(conversationParts.get(0));
                }

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

    private void startConversationPart(final ConversationPart conversationPart) {

        final ConversationPartView view = new ConversationPartView(
                ConversationActivity.this,
                conversationPart
        );

        conversationContainer.addView(view);

        Observable<Long> delayedTimer = Observable.timer(
                conversationPart.getTypingDuration(),
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

    @Subscribe public void onConversationMessageShown(ConversationMessageShown event) {
        int nextPos = event.getPosition() + 1;

        if (nextPos < conversationParts.size()) {
            startConversationPart(conversationParts.get(nextPos));
        }
    }

    @OnClick(R.id.next_question_button)
    public void onSubmitClicked() {
//        storyTeller.next();
    }

}

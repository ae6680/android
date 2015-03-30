package com.shinav.mathapp.conversation;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.shinav.mathapp.R;
import com.shinav.mathapp.injection.InjectedActivity;
import com.shinav.mathapp.injection.modules.ActivityModule;
import com.shinav.mathapp.progress.Storyteller;
import com.shinav.mathapp.repository.RealmRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ConversationActivity extends InjectedActivity {

    @InjectView(R.id.conversation_container) LinearLayout conversationContainer;

    @Inject RealmRepository realmRepository;
    @Inject Storyteller storyTeller;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.inject(this);

        initConversation();
    }

    @Override public ActivityModule getModules() {
        return new ActivityModule(this);
    }

    private void initConversation() {

        String conversationKey = getIntent().getStringExtra(Storyteller.TYPE_KEY);

        final Conversation conversation = realmRepository.getConversation(conversationKey);

        final Handler handler = new Handler();
        Runnable showTypingMessageRunnable = new Runnable() {

            int counter = 0;

            @Override public void run() {

                final List<ConversationEntry> entries = new ArrayList<>(
                        conversation.getConversationEntries());

                ConversationEntry conversationEntry = entries.get(counter);

                final ConversationEntryView view = new ConversationEntryView(
                        ConversationActivity.this,
                        conversationEntry
                );

                conversationContainer.addView(view);

                Animation animation = AnimationUtils.loadAnimation(getBaseContext(), android.R.anim.fade_in);
                view.startAnimation(animation);

                counter++;

                final Runnable parent = this;

                Runnable showMessageRunnable = new Runnable() {
                    @Override public void run() {

                        view.showMessage();

                        if (counter < entries.size()) {
                            handler.postDelayed(parent, entries.get(counter).getDelay());
                        }
                    }
                };

                handler.postDelayed(showMessageRunnable, conversationEntry.getTypingDuration());

            }
        };

        showTypingMessageRunnable.run();

    }

    @OnClick(R.id.next_question_button)
    public void onSubmitClicked() {
        storyTeller.next();
    }

}

package com.shinav.mathapp.conversation;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.shinav.mathapp.R;
import com.shinav.mathapp.progress.Storyteller;
import com.shinav.mathapp.repository.RealmRepository;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ConversationActivity extends Activity {

    @InjectView(R.id.conversation_container) LinearLayout conversationContainer;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.inject(this);

        initConversation();
    }

    private void initConversation() {

        String conversationKey = getIntent().getStringExtra(Storyteller.TYPE_KEY);

        final Conversation conversation = RealmRepository.getInstance().getConversation(conversationKey);

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

    @OnClick(R.id.submit_button)
    public void onSubmitClicked() {
        new Storyteller(this).next();
    }

}

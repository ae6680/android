package com.shinav.mathapp.conversation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.shinav.mathapp.R;
import com.shinav.mathapp.approach.ApproachActivity;

import java.util.Arrays;
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

        final List<ConversationEntry> conversation = Arrays.asList(
                new ConversationEntry("Hey, zullen we op vakantie?", ConversationEntry.LEFT, 1500, 2500),
                new ConversationEntry("Waarheen?", ConversationEntry.RIGHT, 1500, 2000),
                new ConversationEntry("Geen idee, ik zal mijn Maps er eens bij pakken.", ConversationEntry.LEFT, 800, 3000),
                new ConversationEntry("Is er een land waar je niet bent geweest en waar je graag heen wil?", ConversationEntry.RIGHT, 1200, 4500),
                new ConversationEntry("Ja, Spanje bijvoorbeeld!", ConversationEntry.LEFT, 800, 2500)
        );

        final Handler handler = new Handler();
        Runnable showTypingMessageRunnable = new Runnable() {

            int counter = 0;

            @Override public void run() {

                ConversationEntry conversationEntry = conversation.get(counter);

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

                        if (counter < conversation.size()) {
                            handler.postDelayed(parent, conversation.get(counter).getDelay());
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
        startActivity(new Intent(this, ApproachActivity.class));
        overridePendingTransition(R.anim.slide_left_from_outside, R.anim.slide_left_to_outside);
    }

}

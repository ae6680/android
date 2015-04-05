package com.shinav.mathapp.conversation;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.mapper.ConversationMapper;
import com.shinav.mathapp.db.mapper.ConversationPartMapper;
import com.shinav.mathapp.db.pojo.Conversation;
import com.shinav.mathapp.db.pojo.ConversationPart;
import com.shinav.mathapp.progress.Storyteller;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

public class ConversationActivity extends Activity {

    @InjectView(R.id.conversation_container) LinearLayout conversationContainer;
    @InjectView(R.id.conversation_title) TextView conversationTitle;

    @Inject Storyteller storyTeller;
    @Inject ConversationPartMapper conversationPartMapper;
    @Inject ConversationMapper conversationMapper;

    private Subscription conversationPartSubscription;
    private Subscription conversationSubscription;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.inject(this);
    }

    @Override protected void onResume() {
        super.onResume();

        String conversationKey = getIntent().getStringExtra(Storyteller.TYPE_KEY);

        conversationSubscription = conversationMapper.getByKey(conversationKey, new Action1<Conversation>() {
            @Override public void call(Conversation conversation) {
                conversationTitle.setText(conversation.getTitle());
            }
        });

        conversationPartSubscription = conversationPartMapper.getByConversationKey(conversationKey, new Action1<List<ConversationPart>>() {
            @Override public void call(List<ConversationPart> conversationParts) {
                initConversation(conversationParts);
            }
        });
    }

    @Override protected void onPause() {
        super.onPause();
        conversationSubscription.unsubscribe();
        conversationPartSubscription.unsubscribe();
    }

    private void initConversation(final List<ConversationPart> conversationParts) {

        final Handler handler = new Handler();
        Runnable showTypingMessageRunnable = new Runnable() {

            int counter = 0;

            @Override public void run() {

                ConversationPart conversationPart = conversationParts.get(counter);

                final ConversationPartView view = new ConversationPartView(
                        ConversationActivity.this,
                        conversationPart
                );

                conversationContainer.addView(view);

                Animation animation = AnimationUtils.loadAnimation(getBaseContext(), android.R.anim.fade_in);
                view.startAnimation(animation);

                counter++;

                final Runnable parent = this;

                Runnable showMessageRunnable = new Runnable() {
                    @Override public void run() {

                        view.showMessage();

                        if (counter < conversationParts.size()) {
                            handler.postDelayed(parent, conversationParts.get(counter).getDelay());
                        }
                    }
                };

                handler.postDelayed(showMessageRunnable, conversationPart.getTypingDuration());

            }
        };

        showTypingMessageRunnable.run();

    }

    @OnClick(R.id.next_question_button)
    public void onSubmitClicked() {
        storyTeller.next();
    }

}

package com.shinav.mathapp.storytelling;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.shinav.mathapp.conversation.ConversationActivity;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.Story;
import com.shinav.mathapp.db.pojo.StoryPart;
import com.shinav.mathapp.db.repository.StoryPartRepository;
import com.shinav.mathapp.db.repository.StoryRepository;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.question.QuestionActivity;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

public class StorytellingService extends Service {

    public static final String PERSPECTIVE_MALE = "male";
    public static final String PERSPECTIVE_FEMALE = "female";

    public static final String EXTRA_PERSPECTIVE = "perspective";

    public static final String ACTION_START = "start";
    public static final String ACTION_NEXT = "next";

    @Inject StoryRepository storyRepository;
    @Inject StoryPartRepository storyPartRepository;

    private List<StoryPart> storyParts;
    private int currentPosition = -1;

    @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();
        ComponentFactory.getApplicationComponent(this).inject(this);
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_START:
                    fetchStoryForPerspective(
                            intent.getStringExtra(EXTRA_PERSPECTIVE));

                    startNext();
                    break;
                case ACTION_NEXT:
                    startNext();
                    break;
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void fetchStoryForPerspective(String perspective) {
        storyRepository.getByPerspective(perspective).first().subscribe(new Action1<Story>() {
            @Override public void call(Story story) {

                storyPartRepository.getByStoryKey(story.getKey()).first().subscribe(new Action1<List<StoryPart>>() {
                    @Override public void call(List<StoryPart> storyParts) {
                        StorytellingService.this.storyParts = storyParts;
                    }
                });

            }
        });
    }

    private void startNext() {
        if (currentPosition+1 < storyParts.size()) {
            currentPosition++;
            StoryPart storyPart = storyParts.get(currentPosition);
            startBasedOnType(storyPart);
        }
    }

    private void startBasedOnType(StoryPart storyPart) {
        if (storyPart.isQuestion()) {
            start(QuestionActivity.class, storyPart.getTypeKey());

        } else if (storyPart.isConversation()) {
            start(ConversationActivity.class, storyPart.getTypeKey());
        }
    }

    private void start(Class<? extends Activity> cls, String typeKey) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(Tables.StoryPart.TYPE_KEY, typeKey);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

//        ((Activity) context).overridePendingTransition(R.anim.slide_left_from_outside, R.anim.slide_left_to_outside);
    }

//    public String getNextQuestionKey() {
//        StoryPart storyPart = new StoryPart();
//        int counter = current;
//        while (counter+1 < storyParts.size() && !storyPart.isQuestion()) {
//            storyPart = storyParts.get(counter+1);
//            counter++;
//        }
//        return storyPart.getTypeKey();
//    }

}

package com.shinav.mathapp.storytelling;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.shinav.mathapp.conversation.ConversationActivity;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.Storyboard;
import com.shinav.mathapp.db.pojo.StoryboardFrame;
import com.shinav.mathapp.db.repository.StoryboardFrameRepository;
import com.shinav.mathapp.db.repository.StoryboardRepository;
import com.shinav.mathapp.event.SendNextQuestionKey;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.question.QuestionActivity;
import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

public class StorytellingService extends Service {

    public static final String PERSPECTIVE_MALE = "male";
    public static final String PERSPECTIVE_FEMALE = "female";

    public static final String EXTRA_PERSPECTIVE = "perspective";

    public static final String ACTION_START = "start";
    public static final String ACTION_NEXT = "next";
    public static final String ACTION_NEXT_KEY = "next_key";

    @Inject Bus bus;
    @Inject StoryboardRepository storyboardRepository;
    @Inject StoryboardFrameRepository storyboardFrameRepository;

    private List<StoryboardFrame> storyboardFrames;
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
                case ACTION_NEXT_KEY:
                    sendNextQuestionKey();
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void fetchStoryForPerspective(String perspective) {

        Observable<Storyboard> observable =
                storyboardRepository.getByPerspective(perspective).first();

        observable.subscribe(new Action1<Storyboard>() {
            @Override public void call(Storyboard storyboard) {
                fetchStoryboardFrames(storyboard);
            }
        });
    }

    private void fetchStoryboardFrames(Storyboard storyboard) {

        Observable<List<StoryboardFrame>> observable =
                storyboardFrameRepository.getByStoryboardKey(storyboard.getKey()).first();

        observable.subscribe(new Action1<List<StoryboardFrame>>() {
            @Override public void call(List<StoryboardFrame> storyboardFrames) {
                StorytellingService.this.storyboardFrames = storyboardFrames;
            }
        });
    }

    private void sendNextQuestionKey() {
        bus.post(new SendNextQuestionKey(getNextQuestionKey()));
    }

    public String getNextQuestionKey() {
        StoryboardFrame storyboardFrame = new StoryboardFrame();

        int counter = currentPosition;
        while (counter+1 < storyboardFrames.size() && !storyboardFrame.isQuestion()) {
            storyboardFrame = storyboardFrames.get(counter+1);
            counter++;
        }

        return storyboardFrame.getFrameTypeKey();
    }

    private void startNext() {
        if (currentPosition+1 < storyboardFrames.size()) {
            currentPosition++;
            StoryboardFrame storyboardFrame = storyboardFrames.get(currentPosition);
            startBasedOnType(storyboardFrame);
        }
    }

    private void startBasedOnType(StoryboardFrame storyboardFrame) {
        if (storyboardFrame.isQuestion()) {
            start(QuestionActivity.class, storyboardFrame.getFrameTypeKey());

        } else if (storyboardFrame.isConversation()) {
            start(ConversationActivity.class, storyboardFrame.getFrameTypeKey());
        }
    }

    private void start(Class<? extends Activity> cls, String typeKey) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(Tables.StoryboardFrame.FRAME_TYPE_KEY, typeKey);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

//        ((Activity) context).overridePendingTransition(R.anim.slide_left_from_outside, R.anim.slide_left_to_outside);
    }

}

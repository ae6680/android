package com.shinav.mathapp.storytelling;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.shinav.mathapp.conversation.ConversationActivity;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.StoryboardFrame;
import com.shinav.mathapp.db.repository.StoryboardFrameRepository;
import com.shinav.mathapp.db.repository.StoryboardRepository;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.main.MainActivity;
import com.shinav.mathapp.questionApproach.QuestionApproachActivity;
import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

public class StorytellingService extends Service {

    public static final String EXTRA_STORYBOARD_KEY = "extra_storyboard_key";
    public static final String EXTRA_FRAME_TYPE_KEY = "extra_frame_type_key";

    public static final String ACTION_START = "action_start";
    public static final String ACTION_START_FROM = "action_start_from";
    public static final String ACTION_NEXT_FROM = "action_next_from";

    @Inject Bus bus;
    @Inject StoryboardRepository storyboardRepository;
    @Inject StoryboardFrameRepository storyboardFrameRepository;

    private List<StoryboardFrame> storyboardFrames;

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
                    fetchStoryboardFrames(intent.getStringExtra(EXTRA_STORYBOARD_KEY));
                    break;
                case ACTION_START_FROM:
                    startFrom(intent.getStringExtra(EXTRA_FRAME_TYPE_KEY));
                    break;
                case ACTION_NEXT_FROM:
                    nextFrom(intent.getStringExtra(EXTRA_FRAME_TYPE_KEY));
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void fetchStoryboardFrames(String storyboardKey) {

        Observable<List<StoryboardFrame>> observable =
                storyboardFrameRepository.getByStoryboardKey(storyboardKey);

        observable.subscribe(new Action1<List<StoryboardFrame>>() {
            @Override public void call(List<StoryboardFrame> storyboardFrames) {
                StorytellingService.this.storyboardFrames = storyboardFrames;
            }
        });
    }

    private void startFrom(String frameTypeKey) {
        for (StoryboardFrame frame : storyboardFrames) {
            if (frameTypeKey.equals(frame.getFrameTypeKey())) {
                startBasedOnType(frame);
            }
        }
    }

    private void nextFrom(String frameTypeKey) {

        boolean getFrame = false;

        for (StoryboardFrame frame : storyboardFrames) {

            if (getFrame) {
               startBasedOnType(frame);
                return;
            }

            if (frameTypeKey.equals(frame.getFrameTypeKey())) {
                getFrame = true;
            }
        }

        start(MainActivity.class, null);
    }

    private void startBasedOnType(StoryboardFrame storyboardFrame) {

        String frameTypeKey = storyboardFrame.getFrameTypeKey();

        if (storyboardFrame.isQuestion()) {
            start(QuestionApproachActivity.class, frameTypeKey);

        } else if (storyboardFrame.isConversation()) {
            start(ConversationActivity.class, frameTypeKey);
        }
    }

    private void start(Class<? extends Activity> cls, String typeKey) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(Tables.StoryboardFrame.FRAME_TYPE_KEY, typeKey);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

//        ((Activity) context).overridePendingTransition(R.anim.slide_left_from_outside, R.anim.slide_left_to_outside);
    }

}

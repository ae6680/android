package com.shinav.mathapp.tutorial;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.shinav.mathapp.db.dataMapper.TutorialFrameMapper;
import com.shinav.mathapp.db.dataMapper.TutorialMapper;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.Tutorial;
import com.shinav.mathapp.db.pojo.TutorialFrame;
import com.shinav.mathapp.db.repository.TutorialFrameRepository;
import com.shinav.mathapp.db.repository.TutorialRepository;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.main.MainActivity;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

public class TutorialManagingService extends Service {

    public static final String PERSPECTIVE_MALE = "male";
    public static final String PERSPECTIVE_FEMALE = "female";

    public static final String EXTRA_PERSPECTIVE = "perspective";

    public static final String ACTION_START = "start";
    public static final String ACTION_NEXT = "next";
    public static final String ACTION_BACK = "back";

    @Inject TutorialFrameMapper tutorialFrameMapper;
    @Inject TutorialMapper tutorialMapper;

    @Inject TutorialRepository tutorialRepository;
    @Inject TutorialFrameRepository tutorialFrameRepository;

    private List<TutorialFrame> tutorialFrames;
    private int currentPosition = -1;

    @Override public IBinder onBind(Intent intent) { return null; }

    @Override public void onCreate() {
        super.onCreate();
        ComponentFactory.getApplicationComponent(this).inject(this);
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_START:
                    fetchTutorialForPerspective(
                            intent.getStringExtra(EXTRA_PERSPECTIVE));

                    startNext();
                    break;
                case ACTION_NEXT:
                    startNext();
                    break;
                case ACTION_BACK:
                    back();
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void back() {
        currentPosition--;
    }

    private void fetchTutorialForPerspective(String perspective) {
        tutorialRepository.getByPerspective(perspective).first().subscribe(new Action1<Tutorial>() {

            @Override public void call(Tutorial tutorial) {

                tutorialFrameRepository.getByTutorialKey(tutorial.getKey()).first().subscribe(new Action1<List<TutorialFrame>>() {
                    @Override public void call(List<TutorialFrame> tutorialFrames) {
                        Collections.sort(tutorialFrames);
                        TutorialManagingService.this.tutorialFrames = tutorialFrames;
                    }
                });

            }

        });
    }

    private void startNext() {
        if (currentPosition+1 < tutorialFrames.size()) {
            currentPosition++;
            TutorialFrame tutorialFrame = tutorialFrames.get(currentPosition);
            startBasedOnType(tutorialFrame);
        } else {
            startActivity(MainActivity.class, null);
        }
    }

    private void startBasedOnType(TutorialFrame tutorialFrame) {

        switch (tutorialFrame.getFrameType()) {
            case TutorialFrame.CONVERSATION:
                startActivity(TutorialConversationActivity.class, tutorialFrame.getFrameTypeKey());
                break;
            case TutorialFrame.APPROACH:
                startActivity(TutorialApproachActivity.class, tutorialFrame.getFrameTypeKey());
                break;
            case TutorialFrame.APPROACH_FEEDBACK:
                startActivity(TutorialApproachFeedbackActivity.class, tutorialFrame.getFrameTypeKey());
                break;
            case TutorialFrame.QUESTION:
                startActivity(TutorialQuestionActivity.class, tutorialFrame.getFrameTypeKey());
        }

    }

    private void startActivity(Class<? extends Activity> cls, String typeKey) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(Tables.StoryboardFrame.FRAME_TYPE_KEY, typeKey);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

//        ((Activity) this.getApplicationContext()).overridePendingTransition(R.anim.slide_left_from_outside, R.anim.slide_left_to_outside);
    }

}

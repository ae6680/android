package com.shinav.mathapp.tutorial;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.TutorialFrame;
import com.shinav.mathapp.db.repository.TutorialFrameRepository;
import com.shinav.mathapp.injection.component.Injector;
import com.shinav.mathapp.main.MainActivity;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

import static com.shinav.mathapp.MyApplication.PREF_TUTORIAL_COMPLETED;

public class TutorialManagingService extends Service {

    public static final String EXTRA_TUTORIAL_KEY = "extra_tutorial_key";
    public static final String EXTRA_FRAME_TYPE_KEY = "extra_frame_type_key";
    public static final String EXTRA_FRAME_TYPE = "extra_frame_type";

    public static final String ACTION_START = "action_start";
    public static final String ACTION_START_NEXT_FROM = "action_start_next_from";

    @Inject TutorialFrameRepository tutorialFrameRepository;
    @Inject SharedPreferences sharedPreferences;

    private List<TutorialFrame> tutorialFrames;

    @Override public IBinder onBind(Intent intent) { return null; }

    @Override public void onCreate() {
        super.onCreate();
        Injector.getApplicationComponent(this).inject(this);
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_START:
                    fetchTutorialFrames(intent.getStringExtra(EXTRA_TUTORIAL_KEY));
                    break;
                case ACTION_START_NEXT_FROM:
                    startNextFrom(
                            intent.getStringExtra(EXTRA_FRAME_TYPE),
                            intent.getStringExtra(EXTRA_FRAME_TYPE_KEY)
                    );
                    break;
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void fetchTutorialFrames(String tutorialKey) {

        tutorialFrameRepository.getByTutorialKey(tutorialKey, new Action1<List<TutorialFrame>>() {
            @Override public void call(List<TutorialFrame> tutorialFramesList) {
                tutorialFrames = tutorialFramesList;
                startBasedOnType(tutorialFrames.get(0));
            }
        });
    }

    private void startNextFrom(String frameType, String frameTypeKey) {

        boolean getFrame = false;

        for (TutorialFrame frame : tutorialFrames) {

            if (getFrame) {
                startBasedOnType(frame);
                return;
            }

            boolean correctType = frameType.equals(frame.getFrameType());
            boolean correctTypeKey = frameTypeKey.equals(frame.getFrameTypeKey());

            if (correctTypeKey && correctType) {
                getFrame = true;
            }
        }

        saveTutorialCompletion();
        start(MainActivity.class, null);
    }

    private void startBasedOnType(TutorialFrame tutorialFrame) {

        String frameTypeKey = tutorialFrame.getFrameTypeKey();

        switch (tutorialFrame.getFrameType()) {
            case TutorialFrame.CUTSCENE:
                start(TutorialCutsceneActivity.class, frameTypeKey);
                break;
            case TutorialFrame.APPROACH:
                start(TutorialQuestionApproachActivity.class, frameTypeKey);
                break;
            case TutorialFrame.APPROACH_FEEDBACK:
                start(TutorialQAFActivity.class, frameTypeKey);
                break;
            case TutorialFrame.QUESTION:
                start(TutorialQuestionActivity.class, frameTypeKey);
        }

    }

    private void start(Class<? extends Activity> cls, String frameTypeKey) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(Tables.StoryboardFrame.FRAME_TYPE_KEY, frameTypeKey);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void saveTutorialCompletion() {
        sharedPreferences.edit().putBoolean(
                PREF_TUTORIAL_COMPLETED,
                true
        ).apply();
    }

}

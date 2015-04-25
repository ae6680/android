package com.shinav.mathapp.tutorial;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.db.dataMapper.TutorialFrameMapper;
import com.shinav.mathapp.db.dataMapper.TutorialMapper;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.TutorialFrame;
import com.shinav.mathapp.db.repository.TutorialFrameRepository;
import com.shinav.mathapp.db.repository.TutorialRepository;
import com.shinav.mathapp.injection.component.Injector;
import com.shinav.mathapp.main.MainActivity;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;

public class TutorialManagingService extends Service {

    public static final String EXTRA_TUTORIAL_KEY = "extra_tutorial_key";
    public static final String EXTRA_CHOSEN_CHARACTER = "extra_chosen_character";

    public static final String ACTION_START = "start";
    public static final String ACTION_NEXT = "next";
    public static final String ACTION_BACK = "back";

    @Inject TutorialFrameMapper tutorialFrameMapper;
    @Inject TutorialMapper tutorialMapper;

    @Inject TutorialRepository tutorialRepository;
    @Inject TutorialFrameRepository tutorialFrameRepository;

    @Inject SharedPreferences sharedPreferences;

    private List<TutorialFrame> tutorialFrames;
    private int currentPosition = -1;
    private int chosenCharacterResourceId;

    @Override public IBinder onBind(Intent intent) { return null; }

    @Override public void onCreate() {
        super.onCreate();
        Injector.getApplicationComponent(this).inject(this);
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_START:
                    chosenCharacterResourceId = intent.getIntExtra(EXTRA_CHOSEN_CHARACTER, 0);
                    fetchTutorialFrames(intent.getStringExtra(EXTRA_TUTORIAL_KEY));
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

    private void fetchTutorialFrames(String tutorialKey) {

        Observable<List<TutorialFrame>> observable =
                tutorialFrameRepository.getByTutorialKey(tutorialKey).first();

        observable.subscribe(new Action1<List<TutorialFrame>>() {
            @Override public void call(List<TutorialFrame> tutorialFrames) {
                Collections.sort(tutorialFrames);
                TutorialManagingService.this.tutorialFrames = tutorialFrames;
                startNext();
            }
        });
    }

    private void startNext() {
        if (currentPosition+1 < tutorialFrames.size()) {
            currentPosition++;
            TutorialFrame tutorialFrame = tutorialFrames.get(currentPosition);
            startBasedOnType(tutorialFrame);
        } else {
            saveChosenCharacter(chosenCharacterResourceId);
            startActivity(MainActivity.class, null);
        }
    }

    private void startBasedOnType(TutorialFrame tutorialFrame) {

        String frameTypeKey = tutorialFrame.getFrameTypeKey();

        switch (tutorialFrame.getFrameType()) {
            case TutorialFrame.CONVERSATION:
                startActivity(TutorialConversationActivity.class, frameTypeKey);
                break;
            case TutorialFrame.APPROACH:
                startActivity(TutorialQuestionApproachActivity.class, frameTypeKey);
                break;
            case TutorialFrame.APPROACH_FEEDBACK:
                startActivity(TutorialQAFActivity.class, frameTypeKey);
                break;
            case TutorialFrame.QUESTION:
                startActivity(TutorialQuestionActivity.class, frameTypeKey);
        }

    }

    private void startActivity(Class<? extends Activity> cls, String typeKey) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(Tables.StoryboardFrame.FRAME_TYPE_KEY, typeKey);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

//        ((Activity) this.getApplicationContext()).overridePendingTransition(R.anim.slide_left_from_outside, R.anim.slide_left_to_outside);
    }

    private void saveChosenCharacter(int resourceId) {
        sharedPreferences.edit().putInt(
                MyApplication.PREF_CHOSEN_CHARACTER,
                resourceId
        ).apply();
    }


}

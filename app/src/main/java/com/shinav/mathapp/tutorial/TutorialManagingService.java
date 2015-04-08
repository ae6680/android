package com.shinav.mathapp.tutorial;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.shinav.mathapp.db.dataMapper.TutorialMapper;
import com.shinav.mathapp.db.dataMapper.TutorialPartMapper;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.Tutorial;
import com.shinav.mathapp.db.pojo.TutorialPart;
import com.shinav.mathapp.db.repository.TutorialPartRepository;
import com.shinav.mathapp.db.repository.TutorialRepository;
import com.shinav.mathapp.injection.component.ComponentFactory;
import com.shinav.mathapp.main.MainActivity;

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

    @Inject TutorialPartMapper tutorialPartMapper;
    @Inject TutorialMapper tutorialMapper;

    @Inject TutorialRepository tutorialRepository;
    @Inject TutorialPartRepository tutorialPartRepository;

    private List<TutorialPart> tutorialParts;
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

                tutorialPartRepository.getByTutorialKey(tutorial.getKey()).first().subscribe(new Action1<List<TutorialPart>>() {
                    @Override public void call(List<TutorialPart> tutorialParts) {
                        TutorialManagingService.this.tutorialParts = tutorialParts;
                    }
                });

            }

        });
    }

    private void startNext() {
        if (currentPosition+1 < tutorialParts.size()) {
            currentPosition++;
            TutorialPart tutorialPart = tutorialParts.get(currentPosition);
            startBasedOnType(tutorialPart);
        } else {
            startActivity(MainActivity.class, null);
        }
    }

    private void startBasedOnType(TutorialPart tutorialPart) {
        if (tutorialPart.isQuestion()) {
            startActivity(TutorialQuestionActivity.class, tutorialPart.getTypeKey());

        } else if (tutorialPart.isConversation()) {
            startActivity(TutorialConversationActivity.class, tutorialPart.getTypeKey());
        }
    }

    private void startActivity(Class<? extends Activity> cls, String typeKey) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(Tables.StoryPart.TYPE_KEY, typeKey);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

//        ((Activity) this.getApplicationContext()).overridePendingTransition(R.anim.slide_left_from_outside, R.anim.slide_left_to_outside);
    }

}

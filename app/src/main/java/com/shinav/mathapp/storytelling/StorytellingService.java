package com.shinav.mathapp.storytelling;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.shinav.mathapp.cutscene.CutsceneActivity;
import com.shinav.mathapp.db.dataMapper.QuestionMapper;
import com.shinav.mathapp.db.helper.Tables;
import com.shinav.mathapp.db.pojo.Question;
import com.shinav.mathapp.db.pojo.StoryboardFrame;
import com.shinav.mathapp.db.repository.QuestionRepository;
import com.shinav.mathapp.db.repository.StoryboardFrameRepository;
import com.shinav.mathapp.injection.component.Injector;
import com.shinav.mathapp.main.storyboard.StoryboardEndActivity;
import com.shinav.mathapp.questionApproach.QuestionApproachActivity;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

public class StorytellingService extends Service {

    public static final String EXTRA_STORYBOARD_KEY = "extra_storyboard_key";
    public static final String EXTRA_FRAME_TYPE_KEY = "extra_frame_type_key";

    public static final String ACTION_START = "action_start";
    public static final String ACTION_START_FROM = "action_start_from";
    public static final String ACTION_START_NEXT_FROM = "action_start_next_from";
    public static final String ACTION_OPEN_NEXT_QUESTION_FROM = "action_open_next_question_from";

    @Inject StoryboardFrameRepository storyboardFrameRepository;
    @Inject QuestionRepository questionRepository;
    @Inject QuestionMapper questionMapper;

    private List<StoryboardFrame> storyboardFrames = Collections.emptyList();

    @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();
        Injector.getApplicationComponent(this).inject(this);
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
                case ACTION_START_NEXT_FROM:
                    startNextFrom(intent.getStringExtra(EXTRA_FRAME_TYPE_KEY));
                    break;
                case ACTION_OPEN_NEXT_QUESTION_FROM:
                    openNextQuestionFrom(intent.getStringExtra(EXTRA_FRAME_TYPE_KEY));
                    break;
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void fetchStoryboardFrames(String storyboardKey) {
        storyboardFrameRepository.findAllByParent(storyboardKey, new Action1<List<StoryboardFrame>>() {
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

    private void startNextFrom(String frameTypeKey) {

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

        start(StoryboardEndActivity.class, null);
    }

    private void startBasedOnType(StoryboardFrame storyboardFrame) {

        String frameTypeKey = storyboardFrame.getFrameTypeKey();

        if (storyboardFrame.isQuestion()) {
            start(QuestionApproachActivity.class, frameTypeKey);

        } else if (storyboardFrame.isCutscene()) {
            start(CutsceneActivity.class, frameTypeKey);
        }
    }

    private void start(Class<? extends Activity> cls, String typeKey) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(Tables.StoryboardFrame.FRAME_TYPE_KEY, typeKey);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void openNextQuestionFrom(String frameTypeKey) {

        boolean frameToBeOpened = false;

        for (StoryboardFrame frame : storyboardFrames) {

            if (frameToBeOpened && frame.isQuestion()) {

                questionRepository.find(frame.getFrameTypeKey(), new Action1<Question>() {
                    @Override public void call(Question question) {
                        if (question.getProgressState() == Question.STATE_CLOSED) {
                            question.setProgressState(Question.STATE_OPENED);
                            questionMapper.update(question);
                        }
                    }
                });

                return;
            }

            if (frameTypeKey.equals(frame.getFrameTypeKey())) {
                frameToBeOpened = true;
            }
        }
    }

}

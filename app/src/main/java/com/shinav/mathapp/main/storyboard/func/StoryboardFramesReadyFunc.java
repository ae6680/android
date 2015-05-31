package com.shinav.mathapp.main.storyboard.func;

import com.shinav.mathapp.db.pojo.StoryboardFrame;
import com.shinav.mathapp.db.repository.CutsceneRepository;
import com.shinav.mathapp.db.repository.QuestionRepository;
import com.shinav.mathapp.main.storyboard.StoryboardFrameListItem;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

public class StoryboardFramesReadyFunc implements Action1<List<StoryboardFrame>> {

    private CutsceneRepository cutsceneRepository;
    private QuestionRepository questionRepository;
    private Action1<List<StoryboardFrameListItem>> action;

    public StoryboardFramesReadyFunc(
            CutsceneRepository cutsceneRepository,
            QuestionRepository questionRepository,
            Action1<List<StoryboardFrameListItem>> action)
    {
        this.cutsceneRepository = cutsceneRepository;
        this.questionRepository = questionRepository;
        this.action = action;
    }

    @Override public void call(List<StoryboardFrame> storyboardFrames) {

        List<String> questionKeys = new ArrayList<>();
        List<String> cutsceneKeys = new ArrayList<>();

        for (StoryboardFrame storyboardFrame : storyboardFrames) {
            if (storyboardFrame.isQuestion()) {
                questionKeys.add(storyboardFrame.getFrameTypeKey());

            } else if (storyboardFrame.isCutscene()) {
                cutsceneKeys.add(storyboardFrame.getFrameTypeKey());
            }
        }

        Observable<List<StoryboardFrameListItem>> cutsceneObservable = cutsceneRepository
                .findCollection(cutsceneKeys)
                .map(new CutsceneReadyFunc());

        Observable<List<StoryboardFrameListItem>> questionObservable = questionRepository
                .findCollection(questionKeys)
                .map(new QuestionReadyFunc());

        Observable.combineLatest(
                questionObservable,
                cutsceneObservable,
                new StoryboardFrameListItemReadyFunc(storyboardFrames)
        )
                .first()
                .subscribe(action);
    }

}

package com.shinav.mathapp.main.storyboard.func;

import com.shinav.mathapp.db.pojo.Question;
import com.shinav.mathapp.main.storyboard.StoryboardFrameListItem;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

public class QuestionReadyFunc implements Func1<List<Question>, List<StoryboardFrameListItem>> {

    @Override
    public List<StoryboardFrameListItem> call(List<Question> questions) {

        List<StoryboardFrameListItem> listItems = new ArrayList<>();

        for (Question question : questions) {
            listItems.add(createStoryboardListItem(question));
        }

        return listItems;
    }

    private StoryboardFrameListItem createStoryboardListItem(final Question question) {
        return new StoryboardFrameListItem() {

            @Override public String getKey() {
                return question.getKey();
            }

            @Override public String getType() {
                return StoryboardFrameListItem.TYPE_QUESTION;
            }

            @Override public String getTitle() {
                return question.getTitle();
            }

            @Override public int getState() {
                return question.getProgressState();
            }

            @Override public void setState(int state) {
                question.setProgressState(state);
            }

            @Override public String getBackgroundImage() {
                return question.getBackgroundImageUrl();
            }
        };
    }

}


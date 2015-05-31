package com.shinav.mathapp.main.storyboard.func;

import com.shinav.mathapp.db.pojo.Cutscene;
import com.shinav.mathapp.main.storyboard.StoryboardFrameListItem;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

public class CutsceneReadyFunc implements Func1<List<Object>, List<StoryboardFrameListItem>> {

    @Override
    public List<StoryboardFrameListItem> call(List<Object> objects) {

        List<StoryboardFrameListItem> listItems = new ArrayList<>();

        for (Object object : objects) {
            Cutscene cutscene = (Cutscene) object;
            listItems.add(createStoryboardFrameListItem(cutscene));
        }

        return listItems;
    }

    private StoryboardFrameListItem createStoryboardFrameListItem(final Cutscene cutscene) {
        return new StoryboardFrameListItem() {

            @Override public String getKey() {
                return cutscene.getKey();
            }

            @Override public String getType() {
                return StoryboardFrameListItem.TYPE_CUTSCENE;
            }

            @Override public String getTitle() {
                return cutscene.getTitle();
            }

            @Override public int getState() {
                return cutscene.getState();
            }

            @Override public void setState(int state) {
                cutscene.setState(state);
            }

            @Override public String getBackgroundImage() {
                return cutscene.getBackgroundImageUrl();
            }
        };
    }

}

